package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AppointReturnParam implements Serializable {

    private static final long serialVersionUID = -7493753817242602403L;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "预约开始时间")
    private Date startDate;

    @ApiModelProperty(value = "预约结束时间")
    private Date endDate;

    //0.是  1.否  2.再通知
    @ApiModelProperty(value = "是否来检")
    private String isInspects;
}
