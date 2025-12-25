package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 问卷获取详情参数
 */
@Data
public class QuestionnaireParam implements Serializable {
    private static final long serialVersionUID = 5054363384885096612L;

    @ApiModelProperty(value = "手机号(不传)")
    private String phone;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "类型：0问卷1满意度，不填默认0")
    private Integer type;
}
