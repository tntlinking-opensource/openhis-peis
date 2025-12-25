package com.center.medical.statistics.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询职业健康检查结果汇总表 (按危害因素)
 */
@Data
public class SQZrsDto implements Serializable {
    private static final long serialVersionUID = 483875802195433457L;

    @ApiModelProperty(value = "种类名字")
    private String harmClass;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "type0")
    private String type0;

    @ApiModelProperty(value = "type1")
    private String type1;

    @ApiModelProperty(value = "type2")
    private String type2;

    @ApiModelProperty(value = "type4")
    private String type4;

    @ApiModelProperty(value = "type3")
    private String type3;



}


