package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Patienttype;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者类型(Patienttype)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-12-07 19:27:27
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
