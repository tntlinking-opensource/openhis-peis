package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 职业处理意见界面 条件搜索
 */
@Data
public class SearchTreatParam implements Serializable {

    private static final long serialVersionUID = -2797581099818536769L;


    @ApiModelProperty(value = "体检类别")
    private String regimentationNote;


    @ApiModelProperty(value = "健康状况结论")
    private String occupationSummary;


    @ApiModelProperty(value = "危害因素")
    private String occupationDiagnosis;


    @ApiModelProperty(value = "体检号")
    private String patientno;
}
