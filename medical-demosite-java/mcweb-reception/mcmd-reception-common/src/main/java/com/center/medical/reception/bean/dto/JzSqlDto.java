package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询记账sql返回数据
 */
@Data
public class JzSqlDto implements Serializable {
    private static final long serialVersionUID = 3479459707737289066L;

    @ApiModelProperty(value = "体检号（消费）")
    private String patientcode;

    @ApiModelProperty(value = "体检者姓名")
    private String patientname;

    @ApiModelProperty(value = "结算金额")
    private Double jzje;

    @ApiModelProperty(value = "记账人")
    private String jzdwr;

    @ApiModelProperty(value = "记账单位")
    private String jzdw;

    @ApiModelProperty(value = "备注")
    private String note;

}
