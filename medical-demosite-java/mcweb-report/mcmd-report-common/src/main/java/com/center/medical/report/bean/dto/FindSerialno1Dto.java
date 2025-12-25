package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询结论1
 */
@Data
public class FindSerialno1Dto implements Serializable {
    private static final long serialVersionUID = 5437922407817809088L;

    @ApiModelProperty(value = "危害因素名称")
    private String harmName;

    @ApiModelProperty(value = "病名")
    private String occupationDiseast;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "数量")
    private String count;

    @ApiModelProperty(value = "诊断依据")
    private String diagnosis;

    @ApiModelProperty(value = "建议")
    private String summaryText;
}
