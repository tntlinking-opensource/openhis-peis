package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPatienttype;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者类型(MdPatienttype)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:36
 */
public interface MdPatienttypeMapper extends BaseMapper<MdPatienttype> {

    /**
     * 分页查询[体检者类型]列表
     *
     * @param page  分页参数
     * @param param MdPatienttype查询参数
     * @return 分页数据
     */
    IPage<MdPatienttype> getPage(PageParam<MdPatienttype> page, @Param("param") MdPatienttype param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPatienttype getInfoById(@Param("id") String id);

}
