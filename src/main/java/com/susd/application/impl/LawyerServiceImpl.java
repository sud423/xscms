package com.susd.application.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.susd.application.LawyerService;
import com.susd.domain.law.Lawyer;
import com.susd.domain.law.LawyerRepository;
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
public class LawyerServiceImpl implements LawyerService {

    @Autowired
    private LawyerRepository lawyerRepository;

    @Override
    public OptResult save(Lawyer lawyer) {
        if (Validate.isValid(lawyer)) {

            int res;
            if (lawyer.getId() > 0) {
                Lawyer old = lawyerRepository.findById(lawyer.getId());
                if (lawyer.getVersion() != old.getVersion())
                    return OptResult.Failed("信息已修改过，请刷新后重新修改");

                res=lawyerRepository.update(lawyer);
            }
            else {
                lawyer.setVersion(1);
                lawyer.setTenantId(SessionManager.getTenantId());
                lawyer.setAddTime(new Date());
                lawyer.setUserId(SessionManager.getUserId());

                res = lawyerRepository.add(lawyer);
            }

            if(res>0)
                OptResult.Successed();
            return OptResult.Failed("数据保存失败");
        }
        return Validate.verify(lawyer);
    }

    @Override
    public DatatableResult<Lawyer> queryByKeyword(String keyword, DatatableParam param) {
        PageHelper.startPage(param.getPageIndex(), param.getLength(), true);

        List<Lawyer> users = lawyerRepository.findByKeyword(keyword, SessionManager.getTenantId());
        PageInfo<Lawyer> pagedInfo = new PageInfo(users);

        return new DatatableResult(pagedInfo, param.getDraw());
    }
}
