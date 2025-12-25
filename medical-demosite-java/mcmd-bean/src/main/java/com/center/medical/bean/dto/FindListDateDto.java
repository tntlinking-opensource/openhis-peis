package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FindListDateDto implements Serializable {
    private static final long serialVersionUID = 1407695771914702545L;

    @ApiModelProperty(value = "处理意见")
    private String jcjl;

    @ApiModelProperty(value = "结论(名称)")
    private String jbmc;

    @ApiModelProperty(value = "危害因素ID")
    private String occupationDiagnosis;

    @ApiModelProperty(value = "个数")
    private Integer count;

    @ApiModelProperty(value = "危害因素名称")
    private String harmName;

}
