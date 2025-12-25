package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.AppUserPatient;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 小程序用户订单关联表(AppUserPatient)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:12:08
 */
public interface AppUserPatientMapper extends BaseMapper<AppUserPatient> {

    /**
     * 分页查询[小程序用户订单关联表]列表
     *
     * @param page  分页参数
     * @param param AppUserPatient查询参数
     * @return 分页数据
     */
    IPage<AppUserPatient> getPage(PageParam<AppUserPatient> page, @Param("param") AppUserPatient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppUserPatient getInfoById(@Param("id") String id);

}
