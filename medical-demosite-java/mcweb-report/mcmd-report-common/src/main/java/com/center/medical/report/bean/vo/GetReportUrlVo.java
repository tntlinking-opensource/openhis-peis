package com.center.medical.report.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取报告url 返回数据
 */
@Data
public class GetReportUrlVo implements Serializable {
    private static final long serialVersionUID = -9153702837237202702L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "职业/健康")
    private Integer diseaseHealth;

    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "报告pdf地址")
    private String urlPdf;
}
