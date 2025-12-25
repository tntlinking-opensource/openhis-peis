package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 报告交接 获取体检者数据参数
 */
@Data
public class ReportPatientParam implements Serializable {
    private static final long serialVersionUID = -7416826713031185491L;

    @ApiModelProperty(value = "是否补全：true是 false否")
    private String autoFill;

    @ApiModelProperty(value = "类型：0.健康 1.职业(必填！！！)")
    private String diseaseHealth;

    @NotBlank(message = "体检号不能为空")
    @ApiModelProperty(value = "体检号")
    private List<String> patientcode;

    @ApiModelProperty(value = "姓名或输入码")
    private String name;




}
