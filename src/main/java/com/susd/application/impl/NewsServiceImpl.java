package com.susd.application.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.susd.application.NewsService;
import com.susd.domain.site.News;
import com.susd.domain.site.NewsRepository;
import com.susd.domainservice.identity.SessionManager;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;
import com.susd.infrastructure.Validate;

@Service
public class NewsServiceImpl implements NewsService {
	@Autowired
	private NewsRepository newsRepository;
	
	@Override
	public DatatableResult<News> findByKeyword(String keyword, DatatableParam param) {
		PageHelper.startPage(param.getPageIndex(), param.getLength(), true);

		List<News> news=newsRepository.findByKeyword(keyword, SessionManager.getTenantId());
		
		
		PageInfo<News> pageInfo=new PageInfo<News>(news);
		

		return new DatatableResult<News>(pageInfo, param.getDraw());
	}

	@Override
	public OptResult delete(int id) {
		News news=newsRepository.findNewsById(id);
		if(news==null || news.getId()==0)
			return OptResult.Failed("待删除的信息不存在");
		news.setStatus((byte)20);
		int result=newsRepository.update(news);
		
		if(result>0)
			return OptResult.Successed();
		
		return OptResult.Failed("删除失败，请稍候重试");
	}

	@Override
	public OptResult save(News news) {
		if (Validate.isValid(news)) {

			int res = 0;
			news.setStatus((byte)1);
			if (news.getId() == 0) {
				news.setUserId(SessionManager.getUserId());
				news.setAddTime(new Date());
				news.setTenantId(SessionManager.getTenantId());
				news.setVersion(1);
				res=newsRepository.add(news);
			} else {
				News old=newsRepository.findNewsById(news.getId());
				if(old.getVersion()!=news.getVersion())
					return OptResult.Failed("信息已修改过，请刷新后重新修改");
				res=newsRepository.update(news);
			}
			if (res > 0)
				return OptResult.Successed();
			return OptResult.Failed("信息保存失败，请稍候重试");
		}

		return Validate.verify(news);
	}

}
