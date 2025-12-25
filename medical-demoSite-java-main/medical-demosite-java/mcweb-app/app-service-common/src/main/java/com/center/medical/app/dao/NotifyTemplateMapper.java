/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 */

package com.center.medical.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.app.bean.model.NotifyTemplate;
import com.center.medical.app.common.util.PageAdapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lhd
 * @date 2020-07-01 15:44:27
 */
public interface NotifyTemplateMapper extends BaseMapper<NotifyTemplate> {

    /**
     * 统计启用的自定义消息模板数量
     *
     * @return 数量
     */
    long countTagNotify();

    /**
     * 分页获取消息模板
     *
     * @param pageAdapter 分页适配器
     * @return 消息模板列表
     */
    List<NotifyTemplate> pageTagNotify(@Param("adapter") PageAdapter pageAdapter);

    /**
     * 根据id获取模板信息
     *
     * @param templateId
     * @return
     */
    NotifyTemplate getInfoById(@Param("templateId") Long templateId);
}
