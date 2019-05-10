package com.susd.application;

import com.susd.domain.complex.Dict;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

public interface DictService {

    /**
     * 根据关键字分页查询字典
     * @param keyword 关键字
     * @param key 字典键
     * @param param 分页信息
     * @return
     */
    DatatableResult<Dict> findByKeyword(String keyword,String key, DatatableParam param);

    /**
     * 保存字典
     * @param dict 操作字典对象
     * @return
     */
    OptResult save(Dict dict);

    /**
     * 标记字典为删除
     * @param id 字典编号
     * @return
     */
    OptResult delete(int id);
}
