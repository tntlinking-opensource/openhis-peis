package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeispatientConsultation;

import java.util.List;

/**
 * 职业问诊(PeispatientConsultation)服务接口
 *
 * @author ay
 * @since 2024-06-05 14:59:12
 */
public interface OrPeispatientConsultationService extends IService<OrPeispatientConsultation> {

    /**
     * 分页查询[职业问诊]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrPeispatientConsultation> getPage(PageParam<OrPeispatientConsultation> page, OrPeispatientConsultation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrPeispatientConsultation getInfoById(String id);

    /**
     * 通过体检号查询
     * @param patientCode
     * @return
     */
    List<OrPeispatientConsultation> getListByPatientCode(String patientCode);
}

