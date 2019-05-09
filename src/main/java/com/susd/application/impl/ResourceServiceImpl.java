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
import com.susd.application.ResourceService;
import com.susd.domain.identity.Resource;
import com.susd.domain.identity.ResourceRepository;
import com.susd.domainservice.identity.SessionManager;
import com.susd.dto.ResourceItem;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;
import com.susd.infrastructure.Validate;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceRepository resourceRepository;

	@Override
	public DatatableResult<Resource> queryByKeyword(String keyword, DatatableParam param) {
		PageHelper.startPage(param.getPageIndex(), param.getLength(), true);
		List<Resource> resources = resourceRepository.findByKeyword(keyword,SessionManager.getTenantId());

		PageInfo<Resource> pageInfo = new PageInfo<Resource>(resources);

		return new DatatableResult<Resource>(pageInfo, param.getDraw());
	}

	@Override
	public OptResult save(Resource resource) {
		if (Validate.isValid(resource)) {

			int res = 0;

			if (resource.getParentId() == 0)
				resource.setParentIds("0");
			else {
				Resource parent = resourceRepository.findResourceById(resource.getParentId());
				resource.setParentIds(parent.getParentIds()+","+resource.getParentId());
			}
			
			if (resource.getId() == 0) {
				resource.setVersion(1);
				resource.setAddTime(new Date());
				resource.setStatus((byte) 1);
				
				resource.setTenantId(SessionManager.getTenantId());
				res = resourceRepository.add(resource);
			} else {
				Resource old = resourceRepository.findResourceById(resource.getId());
				if (old.getVersion() != resource.getVersion()) {
					return OptResult.Failed("信息已修改过，请刷新后重新修改");
				}
				resource.setStatus(old.getStatus());
				res = resourceRepository.update(resource);
			}
			if (res > 0)
				return OptResult.Successed();
			return OptResult.Failed("信息保存失败，请稍候重试");
		}

		return Validate.verify(resource);
	}

	
	@Override
	public List<ResourceItem> queryToDropDataSrource() {
		List<Resource> resources = resourceRepository.queryToDropDataSrource(SessionManager.getTenantId());
		PropertyMap<Resource, ResourceItem> resourceMap = new PropertyMap<Resource, ResourceItem>() {
			@Override
			protected void configure() {
//				map().setCreated(source.getAddTime());
				map().setpId(source.getParentId());
			}
		};
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(resourceMap);
		

		List<ResourceItem> items = modelMapper.map(resources, new TypeToken<List<ResourceItem>>() {}.getType());
		
		return items;
	}

	
}
