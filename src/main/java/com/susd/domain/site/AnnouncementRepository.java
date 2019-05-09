package com.susd.domain.site;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AnnouncementRepository {
	/**
	 * 根据主键编号获取活动/通知/公告信息
	 * 
	 * @param announcementId 编号
	 * @return 查询到的对象信息
	 */
	Announcement findAnnouncementById(int announcementId);
	
	/**
	 * 根据关键字查询活动
	 * @param keyword
	 * @return
	 */
	List<Announcement> findByKeyword(@Param("keyword") String keyword, @Param("tenantId") int tenantId);
	
	/**
	 * 修改
	 * @param ann
	 * @return
	 */
	int update(Announcement ann);
	
	/**
	 * 更新
	 * @param ann
	 * @return
	 */
	int add(Announcement ann);
}
