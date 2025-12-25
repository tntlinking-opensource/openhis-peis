package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 判断是否有隐私项目 返回数据
 */
@Data
public class PrivateItemDto implements Serializable {
    private static final long serialVersionUID = 2555452639470605126L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检者姓名")
    private String patientName;


}
