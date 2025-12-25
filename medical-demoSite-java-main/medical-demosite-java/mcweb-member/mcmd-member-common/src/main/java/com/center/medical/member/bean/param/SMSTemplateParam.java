package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 获取检前短信模板
 */
@Data
public class SMSTemplateParam implements Serializable {
    private static final long serialVersionUID = 2914715078502249563L;


    @ApiModelProperty(value = "短信模板集合")
    private List<String> messageType;


    @ApiModelProperty(value = "输入码")
    private String key;
}
