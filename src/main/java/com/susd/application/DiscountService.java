package com.susd.application;

import com.susd.domain.activities.Discount;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

public interface DiscountService {
    /**
     * 根据关键字查询班级
     * @param keyword
     * @return
     */
    DatatableResult<Discount> findByKeyword(String keyword, DatatableParam param);

    /**
     * 新增 / 修改
     * @param discount 待保存信息
     * @return 返回json对象
     */
    OptResult save(Discount discount);
}
