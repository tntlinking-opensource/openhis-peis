package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.EmphasisAskSymptom;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC重点询问症状表(EmphasisAskSymptom)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:18:36
 */
public interface EmphasisAskSymptomService extends IService<EmphasisAskSymptom> {

    /**
     * 分页查询[JC重点询问症状表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<EmphasisAskSymptom> getPage(PageParam<EmphasisAskSymptom> page, EmphasisAskSymptom param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    EmphasisAskSymptom getInfoById(String id);

}

