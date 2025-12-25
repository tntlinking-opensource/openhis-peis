package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 手机报告参数
 */
@Data
public class MobileReportParam implements Serializable {
    private static final long serialVersionUID = -6872262661676483010L;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "年份")
    private String year;
}
