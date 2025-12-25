package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 手机报告参数
 */
@Data
public class NewMobileReportParam implements Serializable {
    private static final long serialVersionUID = -6872262661676483010L;

    @ApiModelProperty(value = "手机号(不用传)")
    private String phone;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检")
    private String idExamtype;
}
