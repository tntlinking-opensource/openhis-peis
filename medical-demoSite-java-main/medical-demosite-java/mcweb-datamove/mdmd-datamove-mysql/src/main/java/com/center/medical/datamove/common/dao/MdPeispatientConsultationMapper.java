package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPeispatientConsultation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 职业问诊(MdPeispatientConsultation)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:12
 */
public interface MdPeispatientConsultationMapper extends BaseMapper<MdPeispatientConsultation> {

    /**
     * 分页查询[职业问诊]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientConsultation查询参数
     * @return 分页数据
     */
    IPage<MdPeispatientConsultation> getPage(PageParam<MdPeispatientConsultation> page, @Param("param") MdPeispatientConsultation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatientConsultation getInfoById(@Param("id") String id);

}
