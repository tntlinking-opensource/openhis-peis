package com.center.medical.app.common.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 快递鸟物流详情查询
 * @author SJL
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Quick100 extends SwitchBaseModel {

    /**
     * 快递100中顺丰编码
     */
    @JsonIgnore
    public final static String SF_CODE = "shunfeng";
    /**
     * 快递100中丰网速运编码
     */
    @JsonIgnore
    public final static String FENGWANG_CODE = "fengwang";

    private String customer;
    private String key;
    private String reqUrl;
}
