package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdOccupationSymptom;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC职业症状(MdOccupationSymptom)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:29
 */
public interface MdOccupationSymptomService extends IService<MdOccupationSymptom> {

    /**
     * 分页查询[JC职业症状]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdOccupationSymptom> getPage(PageParam<MdOccupationSymptom> page, MdOccupationSymptom param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdOccupationSymptom getInfoById(String id);

}

