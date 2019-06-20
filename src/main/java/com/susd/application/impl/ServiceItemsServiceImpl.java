package com.susd.application.impl;

import com.susd.application.ServiceItmesService;
import com.susd.domain.law.ServiceItems;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;
import org.springframework.stereotype.Service;

@Service
public class ServiceItemsServiceImpl implements ServiceItmesService {
    @Override
    public OptResult save(ServiceItems item) {
        return null;
    }

    @Override
    public DatatableResult<ServiceItems> queryByKeyword(String keyword, DatatableParam param) {
        return null;
    }
}
