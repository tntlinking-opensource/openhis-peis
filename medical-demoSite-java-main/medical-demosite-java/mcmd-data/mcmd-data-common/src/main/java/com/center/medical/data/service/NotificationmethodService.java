package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Notificationmethod;
import com.center.medical.data.bean.vo.NotificationmethodVo;

import java.util.List;

/**
 * 通知方式（领取方式）表(Notificationmethod)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:33
 */
public interface NotificationmethodService extends IService<Notificationmethod> {

    /**
     * 分页查询[通知方式（领取方式）表]列表
     *
     * @param page  分页参数
     * @param param Notificationmethod查询参数
     * @return 分页数据
     */
    IPage<Notificationmethod> getList(PageParam<Notificationmethod> page, Notificationmethod param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Notificationmethod getInfoById(String id);

    /**
     * 添加或修改
     *
     * @param notificationmethod
     * @return
     */
    String saveOrUpdateNotificationmethod(Notificationmethod notificationmethod);

    /**
     * 逻辑删除
     *
     * @param ids
     * @return
     */
    String removeNotificationmethod(String ids);

    /**
     * 获取所有的通知方式
     * @return
     */
    List<NotificationmethodVo> getIssueWayData();
}

