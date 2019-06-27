package com.susd.application;

import com.susd.domain.site.NewCategories;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

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

}
