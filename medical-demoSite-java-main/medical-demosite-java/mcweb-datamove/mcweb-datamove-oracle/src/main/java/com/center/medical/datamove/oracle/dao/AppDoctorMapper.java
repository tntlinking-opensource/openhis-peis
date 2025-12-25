package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.AppDoctor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 微信小程序医生(AppDoctor)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:11:29
 */
public interface AppDoctorMapper extends BaseMapper<AppDoctor> {

    /**
     * 分页查询[微信小程序医生]列表
     *
     * @param page  分页参数
     * @param param AppDoctor查询参数
     * @return 分页数据
     */
    IPage<AppDoctor> getPage(PageParam<AppDoctor> page, @Param("param") AppDoctor param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppDoctor getInfoById(@Param("id") String id);

}
