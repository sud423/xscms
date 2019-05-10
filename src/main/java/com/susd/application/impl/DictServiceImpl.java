package com.susd.application.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.susd.application.DictService;
import com.susd.domain.complex.Dict;
import com.susd.domain.complex.DictRepository;
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
public class DictServiceImpl implements DictService {

    @Autowired
    private DictRepository dictRepository;

    @Override
    public DatatableResult<Dict> findByKeyword(String keyword, String key, DatatableParam param) {
        PageHelper.startPage(param.getPageIndex(), param.getLength(), true);

        List<Dict> courses=dictRepository.findDictByKey(key,keyword, SessionManager.getTenantId());

        PageInfo<Dict> pageInfo= new PageInfo<>(courses);
        return new DatatableResult<>(pageInfo, param.getDraw());
    }

    @Override
    public OptResult save(Dict dict) {
        if (Validate.isValid(dict)) {

            int res;
            dict.setStatus((byte)1);
            if (dict.getId() == 0) {
                dict.setAddTime(new Date());
                dict.setTenantId(SessionManager.getTenantId());
                dict.setVersion(1);
                res=dictRepository.add(dict);
            } else {
                Dict old=dictRepository.findDictById(dict.getId());
                if(old.getVersion()!=dict.getVersion())
                    return OptResult.Failed("信息已修改过，请刷新后重新修改");
                res=dictRepository.update(dict);
            }
            if (res > 0)
                return OptResult.Successed();
            return OptResult.Failed("信息保存失败，请稍候重试");
        }

        return Validate.verify(dict);

    }

    @Override
    public OptResult delete(int id) {
        Dict dict=dictRepository.findDictById(id);
        if(dict == null || dict.getId() == 0) {
            return OptResult.Failed("待删除的信息不存在");
        }
        dict.setStatus((byte)20);
        int result=dictRepository.update(dict);

        if(result>0)
            return OptResult.Successed();

        return OptResult.Failed("删除失败，请稍候重试");
    }
}
