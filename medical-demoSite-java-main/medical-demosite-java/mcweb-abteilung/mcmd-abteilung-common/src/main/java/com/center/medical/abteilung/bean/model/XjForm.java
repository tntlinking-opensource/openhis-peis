package com.center.medical.abteilung.bean.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class XjForm implements Serializable {


    @ApiModelProperty(value = "id")
    private String id;


    @ApiModelProperty(value = "检查人ID")
    private String rummagerId;


    @ApiModelProperty(value = "检查时间")
    private Date rummagerTime;


    @ApiModelProperty(value = "健康小结")
    private String conclusions;
}
