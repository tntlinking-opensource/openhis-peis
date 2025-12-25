package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.BasMergeConclusion;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (BasMergeConclusion)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:12:21
 */
public interface BasMergeConclusionService extends IService<BasMergeConclusion> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<BasMergeConclusion> getPage(PageParam<BasMergeConclusion> page, BasMergeConclusion param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BasMergeConclusion getInfoById(String id);

}

