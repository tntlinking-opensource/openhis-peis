package com.center.medical.enterprise.common.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class HelpCenterDto implements Serializable {
    private static final long serialVersionUID = -2206526559135271061L;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String value;

}
