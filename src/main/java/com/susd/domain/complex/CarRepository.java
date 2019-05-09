package com.susd.domain.complex;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CarRepository {

	/**
	 * 添加车辆
	 * @param car 待添加的车辆信息
	 * @return 返回受影响行数
	 */
	int add(Car car);
	
	/**
	 * 根据关键字查询车辆列表
	 * @param keyword 关键字
	 */
	List<Car> findByKeyword(@Param("keyword") String keyword, @Param("tenantId") int tenantId);
	
	
	/**
	 * 根据主键获取车辆信息
	 * 
	 * @param carId 车辆编号
	 * @return Car 车辆信息
	 */
	Car findCarById(int carId);

	/**
	 * 更新车辆信息
	 * @param car 待更新车辆
	 * @return 返回受影响行数
	 */
	int update(Car car);
		
	/**
	 * 获取未分配的车牌
	 * @return
	 */
	List<Car> getCars(@Param("tenantId") int tenantId);
}
