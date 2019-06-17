package com.susd.application;


import com.susd.domain.identity.Tenant;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;


public interface TenantService {

    /**
     *  根据关键字分布查询
     * @param keyword 关键字
     * @param param 分页信息
     * @return
     */
    DatatableResult<Tenant> findByKeyword(String keyword, DatatableParam param);

    /**
     * 保存租户信息
     * @param tenant 租户信息
     * @return
     */
    OptResult save(Tenant tenant);

    /**
     * 保存租房对应的资源信息
     * @param tenantId 租户编号
     * @param resourceIds 资源列表
     * @return
     */
    OptResult saveResource(int tenantId,String [] resourceIds);
}
