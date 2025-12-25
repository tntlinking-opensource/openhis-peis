package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通过体检号获取检查项目
 */
@Data
public class InspectionData2ByCodeDto implements Serializable {
    private static final long serialVersionUID = 8397921944112466173L;

    @ApiModelProperty(value = "收费项目ID")
    private String idExamfeeitem;

    @ApiModelProperty(value = "收费项目打印名称")
    private String examfeeitemNameprn;
}
