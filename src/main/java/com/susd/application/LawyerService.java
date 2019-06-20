package com.susd.application;

import com.susd.domain.law.Lawyer;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

public interface LawyerService {

    /**
     * 保存
     * @param lawyer
     * @return
     */
    OptResult save(Lawyer lawyer);

    /**
     * 根据关键字分页查询律师
     * @param keyword
     * @param param
     * @return
     */
    DatatableResult<Lawyer> queryByKeyword(String keyword, DatatableParam param);

}
