package com.center.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.SurrenderDocuments;
import com.center.medical.bean.param.SurrenderDocumentParam;
import com.center.medical.common.utils.page.PageParam;

/**
 * 交单记录表(SurrenderDocuments)服务接口
 *
 * @author ay
 * @since 2024-01-04 15:58:02
 */
public interface SurrenderDocumentsService extends IService<SurrenderDocuments> {

    /**
     * 分页查询[交单记录表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SurrenderDocuments> getPage(PageParam<SurrenderDocuments> page, SurrenderDocumentParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SurrenderDocuments getInfoById(String id);

    /**
     * 通过体检号查询详情
     * @param patientCode
     * @return
     */
    SurrenderDocuments getByPatientCode(String patientCode);
}

