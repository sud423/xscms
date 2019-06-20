package com.susd.application.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.susd.application.ExpressService;
import com.susd.domain.complex.Express;
import com.susd.domain.complex.ExpressRepository;
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
public class ExpressServiceImpl implements ExpressService {

    @Autowired
    private ExpressRepository expressRepository;

    @Override
    public OptResult save(Express express) {
        if (Validate.isValid(express)) {
            if (expressRepository.existsByName(express.getName(), express.getId(), SessionManager.getTenantId())) {
                return OptResult.Failed("“" + express.getName() + "”已被使用，请重新输入物流名称");
            }
            int res;
            if (express.getId() > 0) {
                Express old = expressRepository.findById(express.getId());
                if (express.getVersion() != old.getVersion())
                    return OptResult.Failed("信息已修改过，请刷新后重新修改");

                res=expressRepository.update(express);
            }
            else{
                express.setVersion(1);
                express.setTenantId(SessionManager.getTenantId());
                express.setAddTime(new Date());

                res=expressRepository.add(express);
            }

            if(res>0)
                OptResult.Successed();
            return OptResult.Failed("数据保存失败");
        }
        return Validate.verify(express);
    }

    @Override
    public DatatableResult<Express> queryByKeyword(String keyword, DatatableParam param) {
        PageHelper.startPage(param.getPageIndex(), param.getLength(), true);

        List<Express> users = expressRepository.findByKeyword(keyword, SessionManager.getTenantId());
        PageInfo<Express> pagedInfo = new PageInfo(users);

        return new DatatableResult<Express>(pagedInfo, param.getDraw());
    }
}
