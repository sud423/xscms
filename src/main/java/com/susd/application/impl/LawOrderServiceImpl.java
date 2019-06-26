package com.susd.application.impl;

import com.susd.application.LawOrderService;
import com.susd.domain.law.LawOrder;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import org.springframework.stereotype.Service;

@Service
public class LawOrderServiceImpl implements LawOrderService {
    @Override
    public DatatableResult<LawOrder> queryByKeyword(String keyword, DatatableParam param) {
        return null;
    }
}
