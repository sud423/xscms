package com.susd.application.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.susd.application.NewCategoriesService;
import com.susd.domain.site.NewCategories;
import com.susd.domain.site.NewCategoriesRepository;
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
public class NewCategoryiesServiceImpl implements NewCategoriesService {

    @Autowired
    private NewCategoriesRepository newCategoriesRepository;

    @Override
    public OptResult save(NewCategories category) {
        if (Validate.isValid(category)) {

            if(newCategoriesRepository.existsName(category.getName(),SessionManager.getTenantId(),category.getId()))
                return OptResult.Failed("分类名称已存在，请重新输入");

            int res;
            if (category.getId() > 0) {
                NewCategories old = newCategoriesRepository.findById(category.getId());
                if (category.getVersion() != old.getVersion())
                    return OptResult.Failed("信息已修改过，请刷新后重新修改");

                res=newCategoriesRepository.update(category);
            }
            else {
                category.setVersion(1);
                category.setTenantId(SessionManager.getTenantId());
                category.setAddTime(new Date());
                category.setUserId(SessionManager.getUserId());

                res = newCategoriesRepository.add(category);
            }

            if(res>0)
                return OptResult.Successed();
            return OptResult.Failed("数据保存失败");
        }
        return Validate.verify(category);
    }

    @Override
    public DatatableResult<NewCategories> queryByKeyword(String keyword, DatatableParam param) {
        PageHelper.startPage(param.getPageIndex(), param.getLength(), true);

        List<NewCategories> users = newCategoriesRepository.findByKeyword(keyword, SessionManager.getTenantId());
        PageInfo<NewCategories> pagedInfo = new PageInfo(users);

        return new DatatableResult(pagedInfo, param.getDraw());
    }
}
