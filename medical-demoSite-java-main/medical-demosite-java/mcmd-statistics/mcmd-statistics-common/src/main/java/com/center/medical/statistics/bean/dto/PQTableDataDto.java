package com.center.medical.statistics.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询pacs科室图表数据
 */
@Data
public class PQTableDataDto implements Serializable {
    private static final long serialVersionUID = 8944159967186787403L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "值")
    private String value;

}
