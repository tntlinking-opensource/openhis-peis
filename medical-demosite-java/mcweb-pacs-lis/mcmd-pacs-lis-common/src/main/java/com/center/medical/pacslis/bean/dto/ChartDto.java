package com.center.medical.pacslis.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * pacs登记信息查询图表
 */
@Data
public class ChartDto implements Serializable {
    private static final long serialVersionUID = -4614884425918734972L;


    @ApiModelProperty(value = "体检类型ID，详见：ExamType")
    private String idExamtype;


    @ApiModelProperty(value = "个数")
    private Integer count;
}
