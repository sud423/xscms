package com.susd.domain.site;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NewCategoriesRepository {

    /**
     * 根据主键编号查询资讯分类
     * @param id
     * @return
     */
    NewCategories findById(int id);

    /**
     * 根据关键字查询分类信息
     * @param keyword 关键字
     * @param tenantId 租户编号
     * @return
     */
    List<NewCategories> findByKeyword(@Param("keyword") String keyword, @Param("tenantId") int tenantId);

    /**
     * 检查名称是否存在
     * @param name
     * @param tenantId
     * @param id
     * @return
     */
    boolean existsName(@Param("name")String name,@Param("tenantId")int tenantId,@Param("id")int id);

    /**
     * 添加分类信息
     * @param category 待保存分类对象
     * @return
     */
    int add(NewCategories category);

    /**
     * 修改分类信息
     * @param category
     * @return
     */
    int update(NewCategories category);
}
