package com.center.medical.app.common.bean.event;

import com.center.medical.app.common.bean.SensitiveWord;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 获取敏感词配置的事件
 *
 * @author Citrus
 * @date 2021/8/17 11:24
 */
@Data
@AllArgsConstructor
public class GetSensitiveWordEvent {

    /**
     * 配置key
     */
    private String key;

    /**
     * 敏感词
     */
    private SensitiveWord sensitiveWord;
}
