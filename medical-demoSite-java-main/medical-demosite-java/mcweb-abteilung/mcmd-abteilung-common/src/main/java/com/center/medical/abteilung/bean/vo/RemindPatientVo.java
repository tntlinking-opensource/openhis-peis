package com.center.medical.abteilung.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 职业性问诊-职业病史列表数据返回数据
 */
@Data
public class RemindPatientVo implements Serializable {
    private static final long serialVersionUID = 5668896336524502345L;

    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @ApiModelProperty(value = "体检者名称")
    private String patientname;

    @ApiModelProperty(value = "部门名称")
    private String depName;

    @ApiModelProperty(value = "内容")
    private String content;
}
