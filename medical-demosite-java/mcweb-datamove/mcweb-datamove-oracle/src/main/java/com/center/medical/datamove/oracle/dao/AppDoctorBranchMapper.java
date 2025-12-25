package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.AppDoctorBranch;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 微信小程序医生分中心关联表(AppDoctorBranch)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:11:30
 */
public interface AppDoctorBranchMapper extends BaseMapper<AppDoctorBranch> {

    /**
     * 分页查询[微信小程序医生分中心关联表]列表
     *
     * @param page  分页参数
     * @param param AppDoctorBranch查询参数
     * @return 分页数据
     */
    IPage<AppDoctorBranch> getPage(PageParam<AppDoctorBranch> page, @Param("param") AppDoctorBranch param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppDoctorBranch getInfoById(@Param("id") String id);

}
