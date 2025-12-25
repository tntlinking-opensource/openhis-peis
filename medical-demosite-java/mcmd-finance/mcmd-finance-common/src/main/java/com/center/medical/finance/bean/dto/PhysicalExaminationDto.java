package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 体检产值 数据
 */
@Data
public class PhysicalExaminationDto implements Serializable {
    private static final long serialVersionUID = -8931526509259529634L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String optionsName;

    @ApiModelProperty(value = "总数据及增长率")
    private OVformDto form;

    @ApiModelProperty(value = "每个分中心名称")
    private List<String> barOptions;

    @ApiModelProperty(value = "分中心体检疫苗的产值")
    private List<Double> showdata;

    @ApiModelProperty(value = "分中心体检疫苗的产值(对比数据)")
    private List<Double> showdata2;
}
