package com.susd.application;


import java.util.List;

import com.susd.domain.complex.Car;
import com.susd.dto.CarDto;
import com.susd.dto.SelectDto;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

public interface CarService {

	/**
	 * 关键字查询车辆
	 * @param keyword 关键字
	 * @param page 索引页，下标从1开始
	 * @param size 当前显示几条记录
	 * @return
	 */
	DatatableResult<CarDto> queryByKeyword(String keyword, DatatableParam param);
	
	/**
	 * 保存车辆信息
	 * @param car 待存储车辆信息
	 * @return
	 */
	OptResult save(Car car);
	
	/**
	 * 删除车辆信息
	 * @param carId
	 * @param type
	 * @return
	 */
	OptResult chgStatus(int carId, byte type);
		
	/**
	 * 获取未分配车辆
	 * @return
	 */
	List<SelectDto> getCars();
}
