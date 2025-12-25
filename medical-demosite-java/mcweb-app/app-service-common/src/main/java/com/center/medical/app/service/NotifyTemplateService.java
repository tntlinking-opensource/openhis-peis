/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 */

package com.center.medical.app.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.app.bean.model.NotifyTemplate;
import com.center.medical.app.common.util.PageParam;

/**
 * @author lhd
 * @date 2020-07-01 15:44:27
 */
public interface NotifyTemplateService extends IService<NotifyTemplate> {

    /**
     * 消息模板分页获取
     *
     * @param page 分页参数
     * @return 消息模板分页数据
     */
    IPage<NotifyTemplate> pageTagNotify(PageParam<NotifyTemplate> page);

    /**
     * 根据消息模板id删除
     *
     * @param templateId
     */
    void deleteTemplateInfoById(Long templateId);

    /**
     * 根据id获取模板信息
     *
     * @param templateId
     * @return
     */
    NotifyTemplate getInfoById(Long templateId);

    /**
     * 更新模板信息
     *
     * @param notifyTemplate
     */
    void updateInfoById(NotifyTemplate notifyTemplate);
}
