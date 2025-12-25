package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeispatientConsultation;
import org.apache.ibatis.annotations.Param;

/**
 * 职业问诊(PeispatientConsultation)数据库访问层
 *
 * @author ay
 * @since 2024-06-05 14:59:12
 */
public interface OrPeispatientConsultationMapper extends BaseMapper<OrPeispatientConsultation> {

    /**
     * 分页查询[职业问诊]列表
     *
     * @param page  分页参数
     * @param param PeispatientConsultation查询参数
     * @return 分页数据
     */
    IPage<OrPeispatientConsultation> getPage(PageParam<OrPeispatientConsultation> page, @Param("param") OrPeispatientConsultation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrPeispatientConsultation getInfoById(@Param("id") String id);

}
