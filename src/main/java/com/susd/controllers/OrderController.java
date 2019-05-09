package com.susd.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.susd.application.OrderService;
import com.susd.domain.site.Order;
import com.susd.domain.site.OrderRepository;
import com.susd.dto.OrderDto;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

@RequestMapping(value = "/order")
@Controller
public class OrderController {
	

	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderRepository orderRepository;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "order/index";
	}
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search() {
		return "order/search";
	}
	/**
	 * 获取订单列表
	 * 
	 * @param request 当前HTTP请求
	 * @param keyword 关键字
	 * @param page    索引页，下标从1开始
	 * @param size    当前显示几条记录
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public DatatableResult<OrderDto> query(HttpServletRequest request, DatatableParam param, String keyword) {

		DatatableResult<OrderDto> result = orderService.queryByKeyword(keyword, param);

		return result;
	}

	@RequestMapping(value = { "/edit/{id}", "/edit" }, method = RequestMethod.GET)
	public String edit(Map<String, Object> map,
			@PathVariable(name = "id", required = false) Integer id) {
				if (id!=null&& id > 0) {
					Order order=orderRepository.findOrderById(id);
					map.put("model",order);
				}
		return "order/edit";
	}
	
	/**
	 * 保存数据
	 * 
	 * @param request
	 * @param order    订单信息
	 * @return 返回保存的结果
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public OptResult save(HttpServletRequest request, Order order) {

		return orderService.save(order);
	}
	
	/**
	 * 修改订单状态
	 * @param request
	 * @param orderId 订单编号 
	 * @param status 状态
	 * @return
	 */
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	@ResponseBody
	public OptResult change(HttpServletRequest request,int orderId,byte status) {
		return orderService.chgStatus(orderId,status);
	}

	/**
	 * 修改订单状态
	 * @param request
	 * @param orderId 订单编号 
	 * @param status 状态
	 * @return
	 */
	@RequestMapping(value = "/setDriver", method = RequestMethod.POST)
	@ResponseBody
	public OptResult setDriver(HttpServletRequest request,int orderId,int driverId,int carId) {
		return orderService.setDriver(orderId,driverId,carId);
	}
}
