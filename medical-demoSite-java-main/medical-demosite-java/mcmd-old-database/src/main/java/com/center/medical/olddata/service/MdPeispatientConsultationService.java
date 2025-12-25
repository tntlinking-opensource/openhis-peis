package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdPeispatientConsultation;

/**
 * 职业问诊(MdPeispatientConsultation)服务接口
 *
 * @author ay
 * @since 2024-06-05 15:18:05
 */
public interface MdPeispatientConsultationService extends IService<MdPeispatientConsultation> {

    /**
     * 分页查询[职业问诊]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPeispatientConsultation> getPage(PageParam<MdPeispatientConsultation> page, MdPeispatientConsultation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatientConsultation getInfoById(String id);

}

