package com.susd.domain.bill;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;


public interface BillRepository {
	
	/**
	 * 根据关键字查询自己创建的账单
	 * @param keyword
	 * @param tenantId
	 * @param maintainId
	 * @return
	 */
	List<Bill> findBillByKeyword(@Param("keyword") String keyword, @Param("tenantId") int tenantId, @Param("maintainId") int maintainId);
	
	/**
	 * 保存账单
	 * @param bill
	 * @return
	 */
	int save(Bill bill);
	
	/**
	 * 根据客户编号 及账期查询该客户是否创建账单
	 * @param clientId
	 * @param period
	 * @return
	 */
	Bill findByClient(@Param("clientId") int clientId, @Param("period") String period);
	
	/**
	 * 根据账单编号查询 账单
	 * @param id
	 * @return
	 */
	Bill findById(@Param("id") int id);
	
	/**
	 * 更新总金额
	 * @param billId
	 * @param price
	 * @return
	 */
	int updatePrice(@Param("billId") int billId, @Param("price") float price);
	
	/**
	 * 账单摄像头
	 * @param billId
	 * @return
	 */
	int push(@Param("billId") int billId, @Param("pushId") int pushId, @Param("pushTime") Date pushTime);
}
