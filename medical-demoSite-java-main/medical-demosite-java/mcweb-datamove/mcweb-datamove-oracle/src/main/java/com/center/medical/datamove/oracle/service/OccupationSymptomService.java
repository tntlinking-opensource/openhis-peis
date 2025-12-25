package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.OccupationSymptom;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC职业症状(OccupationSymptom)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:22:21
 */
public interface OccupationSymptomService extends IService<OccupationSymptom> {

    /**
     * 分页查询[JC职业症状]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OccupationSymptom> getPage(PageParam<OccupationSymptom> page, OccupationSymptom param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OccupationSymptom getInfoById(String id);

}

