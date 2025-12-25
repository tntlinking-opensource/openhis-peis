package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.AppLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 微信小程序操作日志(AppLog)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:11:44
 */
public interface AppLogService extends IService<AppLog> {

    /**
     * 分页查询[微信小程序操作日志]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AppLog> getPage(PageParam<AppLog> page, AppLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppLog getInfoById(String id);

}

