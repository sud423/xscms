package com.susd.application.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.susd.application.PersonnelInfoService;
import com.susd.domain.training.PersonnelInfo;
import com.susd.domain.training.PersonnelInfoRepository;
import com.susd.domainservice.identity.SessionManager;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;
import com.susd.infrastructure.Validate;

@Service
public class PersonnelInfoServiceImpl implements PersonnelInfoService {

	@Autowired
	private PersonnelInfoRepository personnelInfoRepository;
	
	@Override
	public DatatableResult<PersonnelInfo> findByKeyword(String keyword, DatatableParam param) {
		PageHelper.startPage(param.getPageIndex(), param.getLength(), true);
		List<PersonnelInfo> priceConfigs = personnelInfoRepository.findByKeyword(keyword,SessionManager.getTenantId());

		PageInfo<PersonnelInfo> pageInfo = new PageInfo<PersonnelInfo>(priceConfigs);

		return new DatatableResult<PersonnelInfo>(pageInfo, param.getDraw());
	}

	@Override
	public OptResult delete(int id) {
		PersonnelInfo info = personnelInfoRepository.findPersonnelInfoById(id);
		if (info == null || info.getId() == 0)
			return OptResult.Failed("数据不存在");

		info.setStatus((byte)2);
		
		int res = personnelInfoRepository.update(info);

		if (res > 0)

			return OptResult.Successed();
		return OptResult.Failed("失败，请稍候重试");
	}

	@Override
	public OptResult save(PersonnelInfo info) {
		if (Validate.isValid(info)) {

			int res = 0;
			if (info.getId() == 0) {
				info.setVersion(1);
				info.setAddTime(new Date());
				info.setTenantId(SessionManager.getTenantId());//所属租户编号
				info.setUserId(SessionManager.getUserId());//维护人员编号
				res = personnelInfoRepository.add(info);
			} else {
				PersonnelInfo old = personnelInfoRepository.findPersonnelInfoById(info.getId());
				if (old.getVersion() != info.getVersion()) {
					return OptResult.Failed("信息已修改过，请刷新后重新修改");
				}
				res = personnelInfoRepository.update(info);
			}
			if (res > 0)
				return OptResult.Successed();
			return OptResult.Failed("信息保存失败，请稍候重试");
		}

		return Validate.verify(info);
	}

}
