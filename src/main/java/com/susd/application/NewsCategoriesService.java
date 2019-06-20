package com.susd.application;

import com.susd.domain.site.NewsCategories;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

public interface NewsCategoriesService {

    /**
     * 保存分类信息
     * @param express
     * @return
     */
    OptResult save(NewsCategories express);

    /**
     * 分页查询分类
     * @param keyword
     * @param param
     * @return
     */
    DatatableResult<NewsCategories> queryByKeyword(String keyword, DatatableParam param);

}
