package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


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

    @ApiModelProperty(value = "体检号集合")
    private List<String> patientCodes;
}
