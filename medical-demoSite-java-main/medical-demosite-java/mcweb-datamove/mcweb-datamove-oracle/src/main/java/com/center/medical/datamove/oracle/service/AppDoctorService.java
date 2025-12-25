package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.AppDoctor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 微信小程序医生(AppDoctor)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:11:30
 */
public interface AppDoctorService extends IService<AppDoctor> {

    /**
     * 分页查询[微信小程序医生]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AppDoctor> getPage(PageParam<AppDoctor> page, AppDoctor param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppDoctor getInfoById(String id);

}

