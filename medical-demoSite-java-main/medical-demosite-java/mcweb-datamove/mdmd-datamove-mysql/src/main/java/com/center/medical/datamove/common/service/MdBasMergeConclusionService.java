package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdBasMergeConclusion;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 合并结伦词中间表(MdBasMergeConclusion)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:08
 */
public interface MdBasMergeConclusionService extends IService<MdBasMergeConclusion> {

    /**
     * 分页查询[合并结伦词中间表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdBasMergeConclusion> getPage(PageParam<MdBasMergeConclusion> page, MdBasMergeConclusion param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBasMergeConclusion getInfoById(String id);

}

