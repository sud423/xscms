package com.susd.application.impl;

import com.susd.application.AttachService;
import com.susd.domain.complex.Attach;
import com.susd.domain.complex.AttachRepository;
import com.susd.domainservice.identity.SessionManager;
import com.susd.infrastructure.OptResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AttachServiceImpl implements AttachService {

    @Autowired
    private AttachRepository attachRepository;

    @Override
    public Attach get(int id,String source,int sort){
        return attachRepository.get(id,source,sort);
    }

    @Override
    public OptResult save(Attach attach) {
        int r ;

        if (attach.getId() > 0) r = attachRepository.update(attach);
        else{
            attach.setTenantId(SessionManager.getTenantId());
            attach.setUploadId(SessionManager.getUserId());
            attach.setAddTime(new Date());
            attach.setLastUpdateTime(new Date());
            r = attachRepository.add(attach);
        }

        if (r > 0)
            return OptResult.Successed();

        return OptResult.Failed("上传失败");
    }
}
