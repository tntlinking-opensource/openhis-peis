/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 */

package com.center.medical.app.bean.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lhd
 * @date 2020-09-12 10:08:46
 */
@Data
@TableName("md_notify_template_tag")
public class NotifyTemplateTag implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 标签消息通知关联表
     */
    @TableId
    private Long notifyShopId;
    /**
     * 标签id
     */
    private Long userTagId;
    /**
     * 模板id
     */
    private Long templateId;

}
