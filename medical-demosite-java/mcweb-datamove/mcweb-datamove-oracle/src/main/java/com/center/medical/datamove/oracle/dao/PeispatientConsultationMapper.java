package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.PeispatientConsultation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 职业问诊(PeispatientConsultation)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:18
 */
public interface PeispatientConsultationMapper extends BaseMapper<PeispatientConsultation> {

    /**
     * 分页查询[职业问诊]列表
     *
     * @param page  分页参数
     * @param param PeispatientConsultation查询参数
     * @return 分页数据
     */
    IPage<PeispatientConsultation> getPage(PageParam<PeispatientConsultation> page, @Param("param") PeispatientConsultation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PeispatientConsultation getInfoById(@Param("id") String id);

}
