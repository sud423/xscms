package com.susd.domain.bill;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BillItemRepository {

	/**
	 * 保存账单项
	 * @param item
	 * @return
	 */
	int save(BillItem item);
	
	/**
	 * 更新
	 * @param item
	 * @return
	 */
	int update(BillItem item);
	
	/**
	 * 保存包裹信息
	 * @param itemId
	 * @param packages
	 * @return
	 */
	int savePack(@Param("itemId") int itemId, @Param("packages") List<BillItemPackage> packages);
	
	/**
	 * 根据包裹编号删除明细
	 * @param itemId
	 * @return
	 */
	int deletePack(int itemId);
		
	/**
	 * 根据账单编号获取账单信息
	 * @param billId
	 * @return
	 */
	List<BillItem> findBillItems(int billId);
	
	/**
	 * 根据主键编号获取账单项
	 * @param id
	 * @return
	 */
	BillItem findById(int id);
}
