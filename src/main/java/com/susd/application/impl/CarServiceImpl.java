package com.susd.application.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.susd.application.CarService;
import com.susd.domain.complex.Car;
import com.susd.domain.complex.CarRepository;
import com.susd.domainservice.identity.SessionManager;
import com.susd.dto.CarDto;
import com.susd.dto.SelectDto;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;
import com.susd.infrastructure.Validate;

@Service
public class CarServiceImpl implements CarService {

	@Autowired
	private CarRepository carRepository;

	@Override
	public DatatableResult<CarDto> queryByKeyword(String keyword, DatatableParam param) {
		PageHelper.startPage(param.getPageIndex(), param.getLength(), true);
		List<Car> cars = carRepository.findByKeyword(keyword,SessionManager.getTenantId());

		
		PageInfo<Car> pageInfo = new PageInfo<Car>(cars);
		
		
		ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        //针对内部list的转换
        Converter<ArrayList<Car>,ArrayList<CarDto>> converter = new AbstractConverter<ArrayList<Car>,ArrayList<CarDto>>() {
            @Override
            protected ArrayList<CarDto> convert(ArrayList<Car> source) {
                return modelMapper.map(source,new TypeToken<ArrayList<CarDto>>(){}.getType());
            }
        };
        PropertyMap<PageInfo<Car>,PageInfo<CarDto>> propertyMap = new PropertyMap<PageInfo<Car>, PageInfo<CarDto>>() {
            @Override
            protected void configure() {
                using(converter).map(source.getList(),destination.getList());
            }
        };
        modelMapper.addMappings(propertyMap);
        modelMapper.createTypeMap(Car.class,CarDto.class);
		
        PageInfo<CarDto> results=modelMapper.map(pageInfo, new TypeToken<PageInfo<CarDto>>() {
        }.getType());
        
		return new DatatableResult<CarDto>(results, param.getDraw());
	}

	@Override
	public OptResult save(Car car) {
		if (Validate.isValid(car)) {

			int res = 0;
			if (car.getId() == 0) {
					car.setStatus((byte) 10);

				car.setAddTime(new Date());
				car.setTenantId(SessionManager.getTenantId());// 所属租户编号
				car.setMaintainId(SessionManager.getUserId());// 维护人编号
				res = carRepository.add(car);
			} else {
				Car old = carRepository.findCarById(car.getId());
				if (old.getVersion() != car.getVersion()) {
					return OptResult.Failed("信息已修改过，请刷新后重新修改");
				}
				car.setStatus(old.getStatus());
				res = carRepository.update(car);
			}
			if (res > 0)
				return OptResult.Successed();
			return OptResult.Failed("信息保存失败，请稍候重试");
		}

		return Validate.verify(car);
	}

	/**
	 * 删除车辆信息
	 * 
	 * @param carId
	 * @return
	 */
	public OptResult chgStatus(int carId, byte type) {
		Car car = carRepository.findCarById(carId);
		if (car == null || carId == 0)
			return OptResult.Failed("未找到车辆");

		car.setStatus((byte) type);// 设置状态为报废
		
		int res = carRepository.update(car);

		if (res > 0)
			return OptResult.Successed();
		return OptResult.Failed("删除失败，请稍候重试");
	}

	@Override
	public List<SelectDto> getCars() {
		List<Car> cars=carRepository.getCars(SessionManager.getTenantId());
		PropertyMap<Car, SelectDto> carMap = new PropertyMap<Car, SelectDto>() {
			@Override
			protected void configure() {
				map().setText(source.getCarNumber());
			}
		};
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(carMap);

		List<SelectDto> dtos = modelMapper.map(cars, new TypeToken<List<SelectDto>>() {
		}.getType());

		return dtos;
	}


	
}
