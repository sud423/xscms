package com.susd.domain.identity;

import java.util.List;

import org.apache.ibatis.annotations.Param;


public interface ResourceRepository {

	int add(Resource resource);
	
	List<Resource> queryToDropDataSrource(@Param("tenantId") int tenantId);
	
	List<Resource> findByKeyword(@Param("keyword") String keyword, @Param("tenantId") int tenantId);
	
	Resource findResourceById(int resourceId);
	
	int update(Resource resource);
}
