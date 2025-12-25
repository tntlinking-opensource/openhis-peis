/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 */

package com.center.medical.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.app.bean.model.NotifyLog;

import java.util.List;

/**
 * @author lhd
 * @date 2020-07-01 15:44:27
 */
public interface NotifyLogService extends IService<NotifyLog> {


    /**
     * 推送消息
     *
     * @param logList 消息列表
     */
    void sendMessage(List<NotifyLog> logList);

    /**
     * 获取还未发送的消息列表
     *
     * @return 消息列表
     */
    List<NotifyLog> listUnSendMsgList();

}
