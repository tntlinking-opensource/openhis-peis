package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Patienttype;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者类型(Patienttype)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:55
 */
public interface PatienttypeMapper extends BaseMapper<Patienttype> {

    /**
     * 分页查询[体检者类型]列表
     *
     * @param page  分页参数
     * @param param Patienttype查询参数
     * @return 分页数据
     */
    IPage<Patienttype> getPage(PageParam<Patienttype> page, @Param("param") Patienttype param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Patienttype getInfoById(@Param("id") String id);

}
