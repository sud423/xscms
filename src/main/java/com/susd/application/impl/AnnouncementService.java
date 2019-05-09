package com.susd.application.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.susd.domain.site.Announcement;
import com.susd.domain.site.AnnouncementRepository;
import com.susd.domainservice.identity.SessionManager;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;
import com.susd.infrastructure.Validate;

@Service
public class AnnouncementService implements com.susd.application.AnnouncementService {

	@Autowired
	private AnnouncementRepository announcementRepository;
	
	@Override
	public DatatableResult<Announcement> findByKeyword(String keyword, DatatableParam param) {
		PageHelper.startPage(param.getPageIndex(), param.getLength(), true);

		List<Announcement> announcements=announcementRepository.findByKeyword(keyword, SessionManager.getTenantId());
		
		
		PageInfo<Announcement> pageInfo=new PageInfo<Announcement>(announcements);
		

		return new DatatableResult<Announcement>(pageInfo, param.getDraw());
	}

	@Override
	public OptResult delete(int id) {
		Announcement ann=announcementRepository.findAnnouncementById(id);
		if(ann==null || ann.getId()==0)
			return OptResult.Failed("待删除的信息不存在");
		ann.setStatus((byte)20);
		int result=announcementRepository.update(ann);
		
		if(result>0)
			return OptResult.Successed();
		
		return OptResult.Failed("删除失败，请稍候重试");
	}

	@Override
	public OptResult save(Announcement ann) {
		if (Validate.isValid(ann)) {

			int res = 0;
			ann.setStatus((byte)1);
			if (ann.getId() == 0) {
				ann.setUserId(SessionManager.getUserId());
				ann.setAddTime(new Date());
				ann.setTenantId(SessionManager.getTenantId());
				ann.setVersion(1);
				res=announcementRepository.add(ann);
			} else {
				Announcement old=announcementRepository.findAnnouncementById(ann.getId());
				if(old.getVersion()!=ann.getVersion())
					return OptResult.Failed("信息已修改过，请刷新后重新修改");
				res=announcementRepository.update(ann);
			}
			if (res > 0)
				return OptResult.Successed();
			return OptResult.Failed("信息保存失败，请稍候重试");
		}

		return Validate.verify(ann);
	}

	
	
}
