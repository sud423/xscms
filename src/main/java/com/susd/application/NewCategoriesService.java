package com.susd.application;

import com.susd.domain.site.NewCategories;
import com.susd.dto.TreeDto;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

import java.util.List;

public interface NewCategoriesService {

    /**
     * 保存分类信息
     * @param category
     * @return
     */
    OptResult save(NewCategories category);

    /**
     * 分页查询分类
     * @param keyword
     * @param param
     * @return
     */
    DatatableResult<NewCategories> queryByKeyword(String keyword, DatatableParam param);

    /**
     * 分类列表
     * @return
     */
    List<TreeDto> queryToDropDataSrource();
}
