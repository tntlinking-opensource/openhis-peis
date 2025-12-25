package com.center.medical.app.common.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 阿里物流详情查询
 *
 * @Author lth
 * @Date 2021/11/11 14:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuickAli extends SwitchBaseModel {

    /**
     * 阿里物流中的顺丰编号
     */
    public final static String SF_CODE = "SFEXPRESS";

    private String aliCode;
    private String reqUrl;
}
