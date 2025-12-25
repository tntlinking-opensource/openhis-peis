package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取职业病史
 */
@Data
public class ExpHead3Dto implements Serializable {
    private static final long serialVersionUID = 6380341513318289331L;

    @ApiModelProperty(value = "建议")
    private String occupationDiseast;

    @ApiModelProperty(value = "诊断日期")
    private Date diagnosisDate;

    @ApiModelProperty(value = "诊断单位")
    private String diagnosisDept;

    @ApiModelProperty(value = "状态")
    private String status;
}
