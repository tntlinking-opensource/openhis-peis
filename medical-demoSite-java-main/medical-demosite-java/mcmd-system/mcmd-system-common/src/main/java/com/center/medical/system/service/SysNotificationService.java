package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.param.AddNotificationParam;
import com.center.medical.common.core.domain.entity.SysNotification;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.param.SysNotificationPageParam;
import com.center.medical.system.bean.vo.SysNotificationPageVo;

/**
 * 通知配置表(SysNoticeConfig)服务接口
 *
 * @author makejava
 * @since 2025-02-26 16:42:50
 */
public interface SysNotificationService extends IService<SysNotification> {

    /**
     * 分页查询[通知配置表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysNotificationPageVo> getPage(PageParam<SysNotificationPageVo> page, SysNotificationPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysNotification getInfoById(String id);

    /**
     * 获取未读的消息
     * @param userNo
     * @return
     */
    Long getUnreadMsg(String userNo);

    /**
     * 添加消息
     */
    void addNotice(AddNotificationParam param);

    /**
     * 全部已读
     * @return
     */
    Boolean allRead();
}

