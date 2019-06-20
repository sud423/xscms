package com.susd.application;

import com.susd.domain.complex.Express;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

public interface ExpressService {

    /**
     * 保存物流信息
     * @param express
     * @return
     */
    OptResult save(Express express);

    /**
     * 分页查询物流信息
     * @param keyword
     * @param param
     * @return
     */
    DatatableResult<Express> queryByKeyword(String keyword, DatatableParam param);
}
