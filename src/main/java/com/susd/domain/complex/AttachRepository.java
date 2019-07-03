package com.susd.domain.complex;

import org.apache.ibatis.annotations.Param;

public interface AttachRepository {
    /**
     * 添加上传文件信息
     * @param attach 上传文件信息
     * @return
     */
    int add(Attach attach);

    /**
     * 更新上传文件信息
     * @param attach 上传文件信息
     * @return
     */
    int update(Attach attach);

    /**
     * 根据来源编号查询附件
     * @param sourceId
     * @param sourceName
     * @param sort
     * @return
     */
    Attach get(@Param("sourceId") int sourceId, @Param("sourceName") String sourceName, @Param("sort") int sort);
}
