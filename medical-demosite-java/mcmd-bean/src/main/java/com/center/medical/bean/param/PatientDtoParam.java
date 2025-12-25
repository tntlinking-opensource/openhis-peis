package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检者查询筛选条件
 * @author: 路飞船长
 * @date: 2024/4/23 09:27
 * @description: 体检者查询筛选条件
 */
public class PatientDtoParam implements Serializable {
    private static final long serialVersionUID = -8255464729706385763L;

    @ApiModelProperty(value = "起始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "个检/团检：0.个检 1.团检")
    private Integer fUsecodehiden;

    @ApiModelProperty(value = "是否登记：0.未登记 1.已登记")
    private Integer fRegistered;

    public PatientDtoParam(Date startTime, Date endTime, Integer fUsecodehiden, Integer fRegistered) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.fUsecodehiden = fUsecodehiden;
        this.fRegistered = fRegistered;
    }
}
