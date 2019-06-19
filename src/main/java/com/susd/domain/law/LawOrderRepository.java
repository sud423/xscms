package com.susd.domain.law;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LawOrderRepository {
    /**
     * 根据主键编号查询咨询订单
     * @param id
     * @return
     */
    LawOrder findById(int id);

    /**
     * 根据关键字查询咨询订单
     * @param keyword 关键字
     * @param tenantId 租户编号
     * @return
     */
    List<LawOrder> findByKeyword(@Param("keyword") String keyword, @Param("tenantId") int tenantId);

    /**
     * 修改分类信息
     * @param category
     * @return
     */
    int update(LawOrder category);
}
