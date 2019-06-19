package com.susd.domain.law;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LawyerServiceRepository {
    /**
     * 根据律师编号查询律师服务内容
     * @param id
     * @return
     */
    LawyerService findById(int id);

    /**
     * 添加律师服务信息
     * @param category 待保存律师服务对象
     * @return
     */
    int add(LawyerService category);

    /**
     * 删除律师服务项
     * @param lawyerId
     * @return
     */
    int delete(int lawyerId);
}
