package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  销售月度目标数据保存或编辑参数
 */
@Data
public class MTSaOrUpParam implements Serializable {
    private static final long serialVersionUID = 8287926802024360742L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "年")
    private String year;

    @ApiModelProperty(value = "target1")
    private Double target1;

    @ApiModelProperty(value = "target2")
    private Double target2;

    @ApiModelProperty(value = "target3")
    private Double target3;

    @ApiModelProperty(value = "target4")
    private Double target4;

    @ApiModelProperty(value = "target5")
    private Double target5;

    @ApiModelProperty(value = "target6")
    private Double target6;

    @ApiModelProperty(value = "target7")
    private Double target7;

    @ApiModelProperty(value = "target8")
    private Double target8;

    @ApiModelProperty(value = "target9")
    private Double target9;

    @ApiModelProperty(value = "target10")
    private Double target10;

    @ApiModelProperty(value = "target11")
    private Double target11;

    @ApiModelProperty(value = "target12")
    private Double target12;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "用户id")
    private String userid;
}
