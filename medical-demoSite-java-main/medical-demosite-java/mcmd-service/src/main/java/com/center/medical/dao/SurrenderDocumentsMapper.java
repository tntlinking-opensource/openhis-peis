package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.SurrenderDocuments;
import com.center.medical.bean.param.SurrenderDocumentParam;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 交单记录表(SurrenderDocuments)数据库访问层
 *
 * @author ay
 * @since 2024-01-04 15:58:02
 */
public interface SurrenderDocumentsMapper extends BaseMapper<SurrenderDocuments> {

    /**
     * 分页查询[交单记录表]列表
     *
     * @param page  分页参数
     * @param param SurrenderDocuments查询参数
     * @return 分页数据
     */
    IPage<SurrenderDocuments> getPage(PageParam<SurrenderDocuments> page, @Param("param") SurrenderDocumentParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SurrenderDocuments getInfoById(@Param("id") String id);

    /**
     * 通过体检号查询详情
     * @param patientCode
     * @return
     */
    SurrenderDocuments getByPatientCode(@Param("patientCode") String patientCode);
}
