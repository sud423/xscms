package com.susd.application.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.susd.application.SysRoleService;
import com.susd.domain.identity.SysRole;
import com.susd.domain.identity.SysRoleRepository;
import com.susd.domainservice.identity.SessionManager;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;
import com.susd.infrastructure.Validate;

@Service
public class SysRoleServiceImpl implements SysRoleService {


	@Autowired
	private SysRoleRepository sysRoleRepository;

	
	@Override
	public DatatableResult<SysRole> queryByKeyword(String keyword, DatatableParam param) {
//		PageHelper.startPage(param.getPageIndex(), param.getLength(), true);
		List<SysRole> roles = sysRoleRepository.findByKeyword(keyword,SessionManager.getTenantId());

		PageInfo<SysRole> pageInfo = new PageInfo<SysRole>(roles);

		return new DatatableResult<SysRole>(pageInfo, param.getDraw());
	}

	@Override
	public OptResult save(SysRole role) {
		if (Validate.isValid(role)) {
			int res = 0;
			if (role.getId() == 0) {
				role.setVersion(1);
				role.setAddTime(new Date());
				role.setStatus((byte) 1);
				role.setType((byte) 1);
				role.setTenantId(SessionManager.getTenantId());
				res = sysRoleRepository.add(role);
			} else {
				SysRole old = sysRoleRepository.findRoleById(role.getId());
				if (old.getVersion() != role.getVersion()) {
					return OptResult.Failed("信息已修改过，请刷新后重新修改");
				}
				role.setStatus(old.getStatus());
				res = sysRoleRepository.update(role);
			}
			if (res > 0)
				return OptResult.Successed();
			return OptResult.Failed("信息保存失败，请稍候重试");
		}

		return Validate.verify(role);
	}

	@Override
	public OptResult savePermission(int roleId, String[] permissionIds) {
		
		sysRoleRepository.deletePermission(roleId);
		
		sysRoleRepository.savePermission(roleId, permissionIds);
		
		return OptResult.Successed();

	}

	
}
