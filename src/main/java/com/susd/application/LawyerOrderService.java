package com.susd.application;

import com.susd.domain.law.LawOrder;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;

public interface LawyerOrderService {

    /**
     * 查询订单
     * @param keyword
     * @param param
     * @return
     */
    DatatableResult<LawOrder> queryByKeyword(String keyword, DatatableParam param);

}
