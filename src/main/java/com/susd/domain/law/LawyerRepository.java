package com.susd.domain.law;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LawyerRepository {
    /**
     * 根据主键编号查询律师信息
     * @param id
     * @return
     */
    Lawyer findById(int id);

    /**
     * 根据关键字查询律师信息
     * @param keyword 关键字
     * @param tenantId 租户编号
     * @return
     */
    List<Lawyer> findByKeyword(@Param("keyword") String keyword, @Param("tenantId") int tenantId);

    /**
     * 添加律师信息
     * @param category 待保存律师对象
     * @return
     */
    int add(Lawyer category);

    /**
     * 修改律师信息
     * @param category
     * @return
     */
    int update(Lawyer category);
}
