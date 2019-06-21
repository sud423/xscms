package com.susd.application.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.susd.application.ServiceItmesService;
import com.susd.domain.law.ServiceItems;
import com.susd.domain.law.ServiceItemsRepository;
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
public class ServiceItemsServiceImpl implements ServiceItmesService {

    @Autowired
    private ServiceItemsRepository serviceItemsRepository;

    @Override
    public OptResult save(ServiceItems item) {
        if (Validate.isValid(item)) {

            int res;
            if (item.getId() > 0) {
                ServiceItems old = serviceItemsRepository.findById(item.getId());
                if (item.getVersion() != old.getVersion())
                    return OptResult.Failed("信息已修改过，请刷新后重新修改");

                res=serviceItemsRepository.update(item);
            }
            else {
                item.setVersion(1);
                item.setTenantId(SessionManager.getTenantId());
                item.setAddTime(new Date());
                item.setUserId(SessionManager.getUserId());

                res = serviceItemsRepository.add(item);
            }

            if(res>0)
                OptResult.Successed();
            return OptResult.Failed("数据保存失败");
        }
        return Validate.verify(item);
    }

    @Override
    public DatatableResult<ServiceItems> queryByKeyword(String keyword, DatatableParam param) {
        PageHelper.startPage(param.getPageIndex(), param.getLength(), true);

        List<ServiceItems> users = serviceItemsRepository.findByKeyword(keyword, SessionManager.getTenantId());
        PageInfo<ServiceItems> pagedInfo = new PageInfo(users);

        return new DatatableResult(pagedInfo, param.getDraw());
    }
}
