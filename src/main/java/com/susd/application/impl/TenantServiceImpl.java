package com.susd.application.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.susd.application.TenantService;
import com.susd.domain.identity.Tenant;
import com.susd.domain.identity.TenantRepository;
import com.susd.domainservice.identity.SessionManager;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;
import com.susd.infrastructure.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TenantServiceImpl implements TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public DatatableResult<Tenant> findByKeyword(String keyword, DatatableParam param) {
        PageHelper.startPage(param.getPageIndex(), param.getLength(), true);
        List<Tenant> priceConfigs = tenantRepository.findByKeyword(keyword);

        PageInfo<Tenant> pageInfo = new PageInfo(priceConfigs);

        return new DatatableResult(pageInfo, param.getDraw());
    }

    @Override
    public OptResult save(Tenant tenant) {
        if (Validate.isValid(tenant)) {

            int res ;
            if (tenant.getId() == 0) {
                tenant.setAddTime(new Date());
                res = tenantRepository.add(tenant);
            } else {

                res = tenantRepository.update(tenant);
            }
            if (res > 0)
                return OptResult.Successed();
            return OptResult.Failed("信息保存失败，请稍候重试");
        }

        return Validate.verify(tenant);
    }
}
