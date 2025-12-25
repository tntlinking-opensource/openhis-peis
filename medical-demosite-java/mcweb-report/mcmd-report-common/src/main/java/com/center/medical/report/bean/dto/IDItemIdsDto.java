package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取检查项目
 */
@Data
public class IDItemIdsDto implements Serializable {
    private static final long serialVersionUID = 2551566516694967282L;

    @ApiModelProperty(value = "体检者收费项目ID")
    private String idExamfeeitem;

    @ApiModelProperty(value = "收费项目打印名称")
    private String examfeeitemNameprn;


}
