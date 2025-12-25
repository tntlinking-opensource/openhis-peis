package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 手机报告详情参数 ,必须全部符合才能查出来
 */
@Data
public class NewMoReportDetailsParam implements Serializable {
    private static final long serialVersionUID = -510738445878864543L;

    @ApiModelProperty(value = "记录id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;
}
