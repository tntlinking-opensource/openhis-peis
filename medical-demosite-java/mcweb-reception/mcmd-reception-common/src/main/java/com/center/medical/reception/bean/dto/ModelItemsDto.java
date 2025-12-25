package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  获取体检者对应的收费项目信息
 */
@Data
public class ModelItemsDto implements Serializable {
    private static final long serialVersionUID = -618454393455507221L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "项目名称")
    private String itemname;

    @ApiModelProperty(value = "样本类型ID")
    private String yblxid;

    @ApiModelProperty(value = "餐序")
    private String cx;

    @ApiModelProperty(value = "科室ID")
    private String idKs;

    @ApiModelProperty(value = "科室")
    private String ks;

    @ApiModelProperty(value = "试管颜色")
    private String bz;
}
