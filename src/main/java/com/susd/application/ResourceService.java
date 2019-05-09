package com.susd.application;

import java.util.List;

import com.susd.domain.identity.Resource;
import com.susd.dto.ResourceItem;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

public interface ResourceService {
	DatatableResult<Resource> queryByKeyword(String keyword, DatatableParam param);
	
	OptResult save(Resource resource);
	
	List<ResourceItem> queryToDropDataSrource();
}
