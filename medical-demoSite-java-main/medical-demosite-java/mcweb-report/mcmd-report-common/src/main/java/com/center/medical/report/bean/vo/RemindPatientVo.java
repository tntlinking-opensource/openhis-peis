package com.center.medical.report.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取提醒接口返回数据
 */
@Data
public class RemindPatientVo implements Serializable {
    private static final long serialVersionUID = 3813049218540266681L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "科室名称")
    private String depName;

    @ApiModelProperty(value = "内容")
    private String content;
}
