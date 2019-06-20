package com.susd.domain.complex;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExpressRepository {
    /**
     * 根据主键编号查询物流公司
     * @param id
     * @return
     */
    Express findById(int id);

    /**
     * 根据关键字查询物流公司
     * @param keyword 关键字
     * @param tenantId 租户编号
     * @return
     */
    List<Express> findByKeyword(@Param("keyword") String keyword, @Param("tenantId") int tenantId);

    /**
     * 检查名称是否被占用
     * @param name
     * @param id
     * @return
     */
    boolean existsByName(@Param("name") String name,@Param("id") int id,@Param("tenantId") int tenantId);


    /**
     * 添加物流公司信息
     * @param category 待保存物流公司对象
     * @return
     */
    int add(Express category);

    /**
     * 修改物流公司信息
     * @param category
     * @return
     */
    int update(Express category);
}
