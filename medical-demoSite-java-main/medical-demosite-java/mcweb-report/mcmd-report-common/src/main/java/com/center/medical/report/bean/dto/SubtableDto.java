package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询子表对象
 */
@Data
public class SubtableDto implements Serializable {
    private static final long serialVersionUID = 2920826484997054347L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "结论词ID")
    private String basconclusionId;
}
