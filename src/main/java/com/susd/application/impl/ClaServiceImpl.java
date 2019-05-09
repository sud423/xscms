package com.susd.application.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.susd.application.ClaService;
import com.susd.domain.training.Cla;
import com.susd.domain.training.ClaRepository;
import com.susd.domainservice.identity.SessionManager;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;
import com.susd.infrastructure.Validate;

@Service
public class ClaServiceImpl implements ClaService {

	@Autowired
	private ClaRepository claRepository;
	
	@Override
	public DatatableResult<Cla> findByKeyword(String keyword, DatatableParam param) {
		PageHelper.startPage(param.getPageIndex(), param.getLength(), true);
		List<Cla> priceConfigs = claRepository.findByKeyword(keyword,SessionManager.getTenantId());

		PageInfo<Cla> pageInfo = new PageInfo<Cla>(priceConfigs);

		return new DatatableResult<Cla>(pageInfo, param.getDraw());
	}

	@Override
	public OptResult delete(int id) {
		Cla project = claRepository.findClaById(id);
		if (project == null || project.getId() == 0)
			return OptResult.Failed("数据不存在");

		project.setStatus((byte)2);
		
		int res = claRepository.update(project);

		if (res > 0)

			return OptResult.Successed();
		return OptResult.Failed("失败，请稍候重试");
	}

	@Override
	public OptResult save(Cla cla) {
		if (Validate.isValid(cla)) {

			int res = 0;
			if (cla.getId() == 0) {
				cla.setVersion(1);
				cla.setAddTime(new Date());
				cla.setTenantId(SessionManager.getTenantId());//所属租户编号
				cla.setUserId(SessionManager.getUserId());//维护人员编号
				res = claRepository.add(cla);
			} else {
				Cla old = claRepository.findClaById(cla.getId());
				if (old.getVersion() != cla.getVersion()) {
					return OptResult.Failed("信息已修改过，请刷新后重新修改");
				}
				res = claRepository.update(cla);
			}
			if (res > 0)
				return OptResult.Successed();
			return OptResult.Failed("信息保存失败，请稍候重试");
		}

		return Validate.verify(cla);
	}

}
