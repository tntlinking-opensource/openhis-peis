package com.center.medical.common.config;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-21 14:17
 * @description: 财务-金蝶相关的配置
 */
public class KingdeeConfig implements Serializable {
    private static final long serialVersionUID = -7197372309408615553L;

    // 财务对接金蝶 获取等级在本中心组织下的客户单位
    public static final String KingdeeUrl = "http://XXX.XXX.XXX.XXX:40050/";
    public static final String isKingdeeUpdate = "1";
}
