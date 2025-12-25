package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 个检报告,科室数据
 */
@Data
public class IReportDto implements Serializable {
    private static final long serialVersionUID = -158938316514698632L;

    @ApiModelProperty(value = "头部,科室名等")
    private Map<String, Object> head;

    @ApiModelProperty(value = "收费项目等")
    private List<Map> item;

    @ApiModelProperty(value = "图片")
    private List<Map> picture;

    @ApiModelProperty(value = "小结")
    private Map<String, Object> summary;
}
