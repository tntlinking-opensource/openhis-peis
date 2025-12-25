package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 职业性问诊-审核保存参数体检者信息
 */
@Data
public class PersonDataDto implements Serializable {
    private static final long serialVersionUID = -956366280507742544L;

    @ApiModelProperty(value = "检查人id")
    private String jcr;

    @ApiModelProperty(value = "检查人姓名")
    private String jcrxm;

    @ApiModelProperty(value = "检查时间")
    private Date jcsj;

    @ApiModelProperty(value = "录入人id")
    private String lrrId;

    @ApiModelProperty(value = "录入人")
    private String lrr;

    @ApiModelProperty(value = "录入时间")
    private Date lrsj;


}
