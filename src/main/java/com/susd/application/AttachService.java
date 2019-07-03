package com.susd.application;

import com.susd.domain.complex.Attach;
import com.susd.infrastructure.OptResult;

public interface AttachService {

    /**
     * 根据所属资源 查询 文件信息
     * @param id 资源编号
     * @param source 资源名称
     * @param sort 排序
     * @return
     */
    Attach get(int id, String source, int sort);

    /**
     * 保存文件信息
     * @param attach
     * @return
     */
    OptResult save(Attach attach);
}
