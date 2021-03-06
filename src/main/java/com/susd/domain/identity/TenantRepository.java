package com.susd.domain.identity;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TenantRepository {

    /**
     * 创建租户
     * @param tenant 所属租户
     * @return
     */
    int add(Tenant tenant);

    /**
     * 根据主键获取租户信息
     * @param tenantId 租户编号
     * @return
     */
    Tenant findTenantById(int tenantId);

    /**
     * 根据关键字查询租户信息
     * @param keyword 关键字
     * @return
     */
    List<Tenant> findByKeyword(@Param("keyword") String keyword);

    /**
     * 修改租房信息
     * @param tenant
     * @return
     */
    int update(Tenant tenant);

    /**
     * 根据租户删除资源
     * @param tenantId
     * @return
     */
    int deleteResource(@Param("tenantId") int tenantId);

    /**
     * 保存租房对应资源信息
     * @param tenantId
     * @param resourceIds
     * @return
     */
    int saveResource(@Param("tenantId") int tenantId,@Param("resourceIds") String[] resourceIds);

}
