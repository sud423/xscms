package com.susd.application.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.susd.application.TeacherService;
import com.susd.domain.training.Teacher;
import com.susd.domain.training.TeacherRepository;
import com.susd.domainservice.identity.SessionManager;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;
import com.susd.infrastructure.Validate;

@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherRepository teacherRepository;
	
	@Override
	public DatatableResult<Teacher> findByKeyword(String keyword, DatatableParam param) {
		PageHelper.startPage(param.getPageIndex(), param.getLength(), true);
		List<Teacher> priceConfigs = teacherRepository.findByKeyword(keyword,SessionManager.getTenantId());

		PageInfo<Teacher> pageInfo = new PageInfo<Teacher>(priceConfigs);

		return new DatatableResult<Teacher>(pageInfo, param.getDraw());
	}

	@Override
	public OptResult delete(int id) {
		Teacher project = teacherRepository.findTeacherById(id);
		if (project == null || project.getId() == 0)
			return OptResult.Failed("数据不存在");

		project.setStatus((byte)2);
		
		int res = teacherRepository.update(project);

		if (res > 0)

			return OptResult.Successed();
		return OptResult.Failed("失败，请稍候重试");
	}

	@Override
	public OptResult save(Teacher teacher) {
		if (Validate.isValid(teacher)) {

			int res = 0;
			if (teacher.getId() == 0) {
				teacher.setVersion(1);
				teacher.setAddTime(new Date());
				teacher.setTenantId(SessionManager.getTenantId());//所属租户编号
				teacher.setUserId(SessionManager.getUserId());//维护人员编号
				res = teacherRepository.add(teacher);
			} else {
				Teacher old = teacherRepository.findTeacherById(teacher.getId());
				if (old.getVersion() != teacher.getVersion()) {
					return OptResult.Failed("信息已修改过，请刷新后重新修改");
				}
				res = teacherRepository.update(teacher);
			}
			if (res > 0)
				return OptResult.Successed();
			return OptResult.Failed("信息保存失败，请稍候重试");
		}

		return Validate.verify(teacher);
	}

}
