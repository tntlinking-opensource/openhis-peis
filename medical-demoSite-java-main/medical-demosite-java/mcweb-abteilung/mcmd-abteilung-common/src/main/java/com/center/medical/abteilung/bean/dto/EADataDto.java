package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 电测听保存检查人小结参数
 */
@Data
public class EADataDto implements Serializable {
    private static final long serialVersionUID = -1072565724896225405L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "是否审核：0.未审核 1.已审核")
    private String isAudit;

    @ApiModelProperty(value = "检查人名称")
    private String rummager;

    @ApiModelProperty(value = "检查人ID")
    private String rummagerId;

    @ApiModelProperty(value = "检查时间")
    private Date rummagerTime;

    @ApiModelProperty(value = "录入人名称")
    private String writeName;

    @ApiModelProperty(value = "录入人ID")
    private String writeId;

    @ApiModelProperty(value = "录入时间")
    private Date writeTime;

    @ApiModelProperty(value = "健康小结")
    private String conclusions;
}
