package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FindFcmxDto implements Serializable {
    private static final long serialVersionUID = 3702757046372230375L;

    @ApiModelProperty(value = "危害因素ID")
    private String occupationDiagnosis;

    @ApiModelProperty(value = "危害因素（拼接）")
    private String harmName;

    @ApiModelProperty(value = "职业禁忌症名称")
    private String diagnosis;

    @ApiModelProperty(value = "处理意见")
    private String summaryText;

    @ApiModelProperty(value = "个数")
    private Integer count;
}
