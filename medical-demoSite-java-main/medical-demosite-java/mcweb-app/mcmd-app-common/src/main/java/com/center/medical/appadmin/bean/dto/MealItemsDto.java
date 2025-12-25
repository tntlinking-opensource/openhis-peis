package com.center.medical.appadmin.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取预约体检者收费项目数据
 */
@Data
public class MealItemsDto implements Serializable {
    private static final long serialVersionUID = 5405680795263360018L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "检查意义")
    private String jcyy;

    @ApiModelProperty(value = "原价")
    private Double price;

    @ApiModelProperty(value = "打印名称")
    private String printName;

    @ApiModelProperty(value = "顺序")
    private Integer shunxu;
}
