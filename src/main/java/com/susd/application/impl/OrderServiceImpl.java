package com.susd.application.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.susd.application.OrderService;
import com.susd.domain.site.Order;
import com.susd.domain.site.OrderRepository;
import com.susd.domainservice.identity.SessionManager;
import com.susd.dto.OrderDto;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;
import com.susd.infrastructure.Validate;


@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public DatatableResult<OrderDto> queryByKeyword(String keyword, DatatableParam param) {
		PageHelper.startPage(param.getPageIndex(), param.getLength(), true);
		List<Order> orders = orderRepository.findByKeyword(keyword, SessionManager.getTenantId());

		PageInfo<Order> pageoInfo = new PageInfo<Order>(orders);
		ModelMapper modelMapper = new ModelMapper();

		Converter<ArrayList<Order>, ArrayList<OrderDto>> converter = new AbstractConverter<ArrayList<Order>, ArrayList<OrderDto>>() {

			@Override
			protected ArrayList<OrderDto> convert(ArrayList<Order> source) {

				PropertyMap<Order, OrderDto> orderMap = new PropertyMap<Order, OrderDto>() {
					@Override
					protected void configure() {
//						try {
//							map().setAddTime(source.getAddTime());

						map().setDriverName(source.getDriver().getName());
						map().setClientName(source.getClient().getName());
						map().setCarNumber(source.getCar().getCarNumber());
//						} catch (ParseException e) {
						// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
					}
				};
				modelMapper.addMappings(orderMap);
				return modelMapper.map(source, new TypeToken<ArrayList<OrderDto>>() {
				}.getType());
			}
		};
		PropertyMap<PageInfo<Order>, PageInfo<OrderDto>> propertyMap = new PropertyMap<PageInfo<Order>, PageInfo<OrderDto>>() {
			@Override
			protected void configure() {
				using(converter).map(source.getList(), destination.getList());
			}
		};
		modelMapper.addMappings(propertyMap);

		PageInfo<OrderDto> pageInfo = modelMapper.map(pageoInfo, new TypeToken<PageInfo<OrderDto>>() {
		}.getType());

		return new DatatableResult<OrderDto>(pageInfo, param.getDraw());
	}

	@Override
	public OptResult save(Order order) {
		if (Validate.isValid(order)) {

			int res = 0;
			if (order.getId() == 0) {
				if (order.getDriverId() != 0)
					order.setStatus((byte) 10);// 已派单
				else
					order.setStatus((byte) 1);
				order.setAddTime(new Date());
				order.setTenantId(SessionManager.getTenantId());// 所属租户编号
				order.setUserId(SessionManager.getUserId());// 下单人编号
				res = orderRepository.add(order);
			}

			if (res > 0)
				return OptResult.Successed();
			return OptResult.Failed("信息保存失败，请稍候重试");
		}

		return Validate.verify(order);
	}

	/**
	 * 更新状态信息
	 * 
	 * @param orderId
	 * @param opType  状态 1：下单成功 10：已派车 20：已接单30：已揽件40：作废
	 * @return
	 */
	public OptResult chgStatus(int orderId, byte status) {
		Order order = orderRepository.findOrderById(orderId);
		if (order == null || order.getId() == 0)
			return OptResult.Failed("订单不存在");

		int res = orderRepository.chgStatus(orderId, status);

		if (res > 0)
			return OptResult.Successed();
		return OptResult.Failed("失败，请稍候重试");
	}

	@Override
	public OptResult setDriver(int orderId, int driverId, int carId) {
		Order order = orderRepository.findOrderById(orderId);
		if (order == null || order.getId() == 0)
			return OptResult.Failed("订单不存在");
		int res = orderRepository.setDriver(orderId, driverId, carId);

		if (res > 0)
			return OptResult.Successed();
		return OptResult.Failed("失败，请稍候重试");
	}

}
