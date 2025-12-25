package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Notificationmethod;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 通知方式（领取方式）表(Notificationmethod)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:22:10
 */
public interface NotificationmethodService extends IService<Notificationmethod> {

    /**
     * 分页查询[通知方式（领取方式）表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Notificationmethod> getPage(PageParam<Notificationmethod> page, Notificationmethod param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Notificationmethod getInfoById(String id);

}

