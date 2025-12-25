package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 校验提取码 参数
 */
@Data
public class ValidationCodeParam implements Serializable {
    private static final long serialVersionUID = 8589073430039385397L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "提取码")
    private String extractedCode;

    @ApiModelProperty(value = "单位名称")
    private String orgName;

    @ApiModelProperty(value = "部门")
    private String orgDepart;

    @ApiModelProperty(value = "姓名")
    private String patientname;

}
