package com.center.medical.center.qingdao.profession.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * TjRenMKeshiXJ
 */
@Data
public class TjRenMKeshiXJ implements Serializable {
    private static final long serialVersionUID = 3483557832191517384L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "收费项目Id")
    private String feeId;

    @ApiModelProperty(value = "职业小结")
    private String zyConclusions;

    @ApiModelProperty(value = "录入人")
    private String lrr;

    @ApiModelProperty(value = "录入时间")
    private Date writeTime;
}
