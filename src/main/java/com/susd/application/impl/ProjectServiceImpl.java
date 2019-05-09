package com.susd.application.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.susd.application.ProjectService;
import com.susd.domain.training.Project;
import com.susd.domain.training.ProjectRepository;
import com.susd.domainservice.identity.SessionManager;
import com.susd.dto.SelectDto;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;
import com.susd.infrastructure.Validate;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public DatatableResult<Project> findByKeyword(String keyword, DatatableParam param) {
		PageHelper.startPage(param.getPageIndex(), param.getLength(), true);
		List<Project> priceConfigs = projectRepository.findByKeyword(keyword,SessionManager.getTenantId());

		PageInfo<Project> pageInfo = new PageInfo<Project>(priceConfigs);

		return new DatatableResult<Project>(pageInfo, param.getDraw());
	}

	@Override
	public OptResult delete(int id) {

		Project project = projectRepository.findProjectById(id);
		if (project == null || project.getId() == 0)
			return OptResult.Failed("数据不存在");

		project.setStatus((byte)2);
		
		int res = projectRepository.update(project);

		if (res > 0)

			return OptResult.Successed();
		return OptResult.Failed("失败，请稍候重试");

	}

	@Override
	public OptResult save(Project project) {
		if (Validate.isValid(project)) {

			int res = 0;
			if (project.getId() == 0) {
				project.setVersion(1);
				project.setStatus((byte)1);
				project.setAddTime(new Date());
				project.setTenantId(SessionManager.getTenantId());//所属租户编号
				project.setUserId(SessionManager.getUserId());//维护人员编号
				res = projectRepository.add(project);
			} else {
				Project old = projectRepository.findProjectById(project.getId());
				if (old.getVersion() != project.getVersion()) {
					return OptResult.Failed("信息已修改过，请刷新后重新修改");
				}
				res = projectRepository.update(project);
			}
			if (res > 0)
				return OptResult.Successed();
			return OptResult.Failed("信息保存失败，请稍候重试");
		}

		return Validate.verify(project);
	}

	@Override
	public List<SelectDto> getAll(String keyword) {
		
		List<Project> projects=projectRepository.findByKeyword(keyword, SessionManager.getTenantId());
		
		PropertyMap<Project, SelectDto> userMap = new PropertyMap<Project, SelectDto>() {
			@Override
			protected void configure() {
				map().setText(source.getName());
			}
		};
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(userMap);

		List<SelectDto> dtos = modelMapper.map(projects, new TypeToken<List<SelectDto>>() {
		}.getType());

		return dtos;
	}

}
