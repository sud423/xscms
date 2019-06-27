package com.susd.domain.law;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ServiceItemsRepository {
    /**
     * 根据主键编号查询服务项目
     * @param id
     * @return
     */
    ServiceItems findById(@Param("id")int id);

    /**
     * 根据关键字查询服务项目
     * @param keyword 关键字
     * @param tenantId 租户编号
     * @return
     */
    List<ServiceItems> findByKeyword(@Param("keyword") String keyword, @Param("tenantId") int tenantId);

    /**
     * 判断名称是否判断
     * @param name
     * @return
     */
    boolean existsName(@Param("name") String name,@Param("tenantId")int tenantId,@Param("id") int id);

    /**
     * 添加服务项目信息
     * @param category 待保存分服务项目对象
     * @return
     */
    int add(ServiceItems category);

    /**
     * 修改服务项目信息
     * @param category
     * @return
     */
    int update(ServiceItems category);
}
