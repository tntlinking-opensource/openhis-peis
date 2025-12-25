package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 个检危急值回访添加保存参数
 */
@Data
public class PositiveTracktParam implements Serializable {

    private static final long serialVersionUID = -5759194849056589268L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "回访备注")
    private String memo;

    @ApiModelProperty(value = "回访时间")
    private Date visitTime;



}
