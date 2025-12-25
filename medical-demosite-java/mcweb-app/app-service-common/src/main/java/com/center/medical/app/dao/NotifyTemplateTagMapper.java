/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 */

package com.center.medical.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.app.bean.model.NotifyTemplate;
import com.center.medical.app.bean.model.NotifyTemplateTag;
import com.center.medical.app.bean.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lhd
 * @date 2020-09-12 10:08:46
 */
public interface NotifyTemplateTagMapper extends BaseMapper<NotifyTemplateTag> {

    /**
     * 批量保存消息模板
     *
     * @param notifyTemplate 模板参数
     */
    void insertBatch(@Param("notifyTemplate") NotifyTemplate notifyTemplate);

    /**
     * 获取模板标签下的相关用户
     *
     * @param templateId 模板id
     * @return 用户列表
     */
    List<User> getUserListByTemplateId(@Param("templateId") Long templateId);
}
