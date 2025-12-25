package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BasMergeConclusion;

import java.util.List;

/**
 * 合并结伦词中间表(BasMergeConclusion)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:37
 */
public interface BasMergeConclusionService extends IService<BasMergeConclusion> {

    /**
     * 分页查询[合并结伦词中间表]列表
     *
     * @param page  分页参数
     * @param param BasMergeConclusion查询参数
     * @return 分页数据
     */
    IPage<BasMergeConclusion> getList(PageParam<BasMergeConclusion> page, BasMergeConclusion param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BasMergeConclusion getInfoById(String id);

    /**
     * 获取历史
     *
     * @param smid
     * @param dh
     * @return
     */
    List<BasMergeConclusion> getHistory(String smid, int dh, String patientarchiveno);

    /**
     * 获取合并的名称
     *
     * @param conclusionId
     * @return
     */
    List<String> getMergeConbination(String conclusionId);


}

