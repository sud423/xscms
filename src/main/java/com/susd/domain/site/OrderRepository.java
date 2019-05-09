package com.susd.domain.site;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OrderRepository {

	/**
	 * 添加订单
	 * @param  待添加的订单信息
	 * @return 返回受影响行数
	 */
	int add(Order order);
	
	/**
	 * 根据关键字查询订单列表
	 * @param keyword 关键字
	 */
	List<Order> findByKeyword(@Param("keyword") String keyword, @Param("tenantId") int tenantId);
	
	
	/**
	 * 根据主键获取订单信息
	 * 
	 * @param orderId 订单编号
	 * @return Order 订单信息
	 */
	Order findOrderById(int orderId);

	/**
	 * 更新状态
	 * @param orderId 订单编号
	 * @param status 状态
	 * @return
	 */
	int chgStatus(@Param("orderId") int orderId, @Param("status") byte status);
	
	/**
	 * 分配驾驶员
	 * @param orderId
	 * @param driverId
	 * @return
	 */
	int setDriver(@Param("orderId") int orderId, @Param("driverId") int driverId, @Param("carId") int carId);
}
