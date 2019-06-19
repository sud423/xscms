package com.susd.domain.site;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NewsCategoriesRepository {

    /**
     * 根据主键编号查询资讯分类
     * @param id
     * @return
     */
    NewsCategories findById(int id);

    /**
     * 根据关键字查询分类信息
     * @param keyword 关键字
     * @param tenantId 租户编号
     * @return
     */
    List<NewsCategories> findByKeyword(@Param("keyword") String keyword,@Param("tenantId") int tenantId);

    /**
     * 添加分类信息
     * @param category 待保存分类对象
     * @return
     */
    int add(NewsCategories category);

    /**
     * 修改分类信息
     * @param category
     * @return
     */
    int update(NewsCategories category);
}
