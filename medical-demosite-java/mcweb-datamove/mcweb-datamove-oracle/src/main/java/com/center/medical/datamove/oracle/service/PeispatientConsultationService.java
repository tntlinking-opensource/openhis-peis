package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.PeispatientConsultation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 职业问诊(PeispatientConsultation)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:23:18
 */
public interface PeispatientConsultationService extends IService<PeispatientConsultation> {

    /**
     * 分页查询[职业问诊]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<PeispatientConsultation> getPage(PageParam<PeispatientConsultation> page, PeispatientConsultation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PeispatientConsultation getInfoById(String id);

}

