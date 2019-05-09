package com.susd.application;

import com.susd.domain.site.Order;
import com.susd.dto.OrderDto;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

public interface OrderService {

	/**
	 * 关键字查询订单信息
	 * @param keyword 关键字
	 * @param page 索引页，下标从1开始
	 * @param size 当前显示几条记录
	 * @return
	 */
	DatatableResult<OrderDto> queryByKeyword(String keyword, DatatableParam param);
	
	/**
	 * 保存订单信息
	 * @param order 待存储订单信息
	 * @return
	 */
	OptResult save(Order order);

	/**
	 * 更新状态信息
	 * @param orderId
	 * @param status 状态 1：下单成功 10：已派车  20：已接单30：已揽件 40：作废
	 * @return
	 * */
	OptResult chgStatus(int orderId, byte status);
	
	/**
	 * 分配驾驶员
	 * @param orderId
	 * @param driverId
	 * @return
	 */
	OptResult setDriver(int orderId, int driverId, int carId);
}
