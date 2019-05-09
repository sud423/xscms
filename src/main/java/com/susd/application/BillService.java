package com.susd.application;


import com.susd.dto.BillDto;
import com.susd.dto.BillItemInputDto;
import com.susd.dto.BillItemOutDto;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

public interface BillService {
	/**
	 * 根据关键字查询账单
	 * @param keyword
	 * @param param
	 * @return
	 */
	DatatableResult<BillDto> queryByKeyword(String keyword, DatatableParam param);
	
	/**
	 * 计算运费 
	 * @param input 账单明细
	 * @return
	 */
	BillItemInputDto countFee(BillItemInputDto input);
	
	/**
	 * 保存
	 * @param input 
	 * @return
	 */
	OptResult save(BillItemInputDto input);
	
	/**
	 * 账单摄像头
	 * @param billId
	 * @return
	 */
	OptResult push(int billId);
	
	/**
	 * 根据账单编号获取账单信息
	 * @param billId
	 * @return
	 */
	DatatableResult<BillItemOutDto> findBillItems(DatatableParam param, int billId);
	
}
