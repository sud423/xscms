package com.susd.application;

import com.susd.domain.law.ServiceItems;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

public interface ServiceItemsService {

    /**
     * 保存服务项内容数据
     * @param item 服务项
     * @return
     */
    OptResult save(ServiceItems item);

    /**
     * 分页查询服务项目
     * @param keyword
     * @param param
     * @return
     */
    DatatableResult<ServiceItems> queryByKeyword(String keyword, DatatableParam param);


}
