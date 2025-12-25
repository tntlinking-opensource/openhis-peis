package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RBGriddataDto implements Serializable {
    private static final long serialVersionUID = -3039626233563789350L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "价格")
    private Double price;

    @ApiModelProperty(value = "名称")
    private String name;

}
