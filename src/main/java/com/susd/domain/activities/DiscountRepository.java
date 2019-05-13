package com.susd.domain.activities;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DiscountRepository {

    /**
     * 添加新优惠信息
     * @param discount
     * @return
     */
    int add(Discount discount);

    /**
     * 修改优惠信息
     * @param discount
     * @return
     */
    int update(Discount discount);

    /**
     * 删除
     * @param id 根据主键删除
     * @return
     */
    int delete(int id);

    /**
     * 根据主键查询优惠信息
     * @param id 主键值
     * @return
     */
    Discount findById(int id);

    /**
     * 根据关键字和租房查询折扣
     * @param keyword 关键字
     * @param tenantId 租户编号
     * @return
     */
    List<Discount> findByKeyword(@Param("keyword") String keyword, @Param("tenantId") int tenantId);
}
