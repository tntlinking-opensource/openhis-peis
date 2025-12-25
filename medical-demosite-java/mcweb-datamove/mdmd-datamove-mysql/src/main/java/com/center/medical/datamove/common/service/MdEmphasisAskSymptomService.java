package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdEmphasisAskSymptom;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC重点询问症状表(MdEmphasisAskSymptom)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:15
 */
public interface MdEmphasisAskSymptomService extends IService<MdEmphasisAskSymptom> {

    /**
     * 分页查询[JC重点询问症状表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdEmphasisAskSymptom> getPage(PageParam<MdEmphasisAskSymptom> page, MdEmphasisAskSymptom param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdEmphasisAskSymptom getInfoById(String id);

}

