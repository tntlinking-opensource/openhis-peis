package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 报告签名配置
 */
@Data
public class ReportSignatureConfigDto implements Serializable {
    private static final long serialVersionUID = -1107952879006040518L;

    @ApiModelProperty(value = "单签名的科室")
    private List<NursingRegistrationDto> param;


    @ApiModelProperty(value = "显示审核者的科室")
    private List<NursingRegistrationDto> reviewerParam;


    @ApiModelProperty(value = "显示检查者的科室")
    private List<NursingRegistrationDto> examinerParam;

}
