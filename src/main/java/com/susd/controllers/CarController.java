package com.susd.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.susd.application.CarService;
import com.susd.domain.complex.Car;
import com.susd.domain.complex.CarRepository;
import com.susd.dto.CarDto;
import com.susd.dto.SelectDto;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

@RequestMapping(value = "/car")
@Controller
public class CarController {
	
	@Autowired
	private CarService carService;
	@Autowired
	private CarRepository carRepository;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		
		return "car/index";
	}
	/**
	 * 获取车辆列表
	 * 
	 * @param request 当前HTTP请求
	 * @param keyword 关键字
	 * @param page    索引页，下标从1开始
	 * @param size    当前显示几条记录
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public DatatableResult<CarDto> query(HttpServletRequest request, DatatableParam param, String keyword) {

		DatatableResult<CarDto> result = carService.queryByKeyword(keyword, param);

		return result;
	}

	@RequestMapping(value = { "/edit/{id}", "/edit" }, method = RequestMethod.GET)
	public String edit(Map<String, Object> map,
			@PathVariable(name = "id", required = false) Integer id) {
				if (id!=null&& id > 0) {
					Car car=carRepository.findCarById(id);
					map.put("model",car);
		}
		return "car/edit";
	}

	/**
	 * 保存数据
	 * 
	 * @param request
	 * @param car    车辆信息
	 * @return 返回保存的结果
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public OptResult save(HttpServletRequest request, Car car) {

		return carService.save(car);
	}
	
	/**
	 * 删除车辆
	 * @param request
	 * @param carId 车辆编号  (操作类型 1：空闲 10：使用中 20：报废)
	 * @return
	 */
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	@ResponseBody
	public OptResult change(HttpServletRequest request,int carId,byte type) {
		return carService.chgStatus(carId,type);
	}
	
	/**
	 * 获取未分配车牌
	 * @return
	 */
	@RequestMapping(value = "/getCars", method = RequestMethod.GET)
	@ResponseBody
	public List<SelectDto> getCars(){
		return carService.getCars();
	}
}
