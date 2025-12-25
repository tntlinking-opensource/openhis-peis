package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BackInfoDataVo implements Serializable {
    private static final long serialVersionUID = -2392770038576492804L;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "结算金额")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "优惠总价")
    private Double totalss;

    @ApiModelProperty(value = "回访备注")
    private String memo;

    @ApiModelProperty(value = "开单医生")
    private String kdys;

    @ApiModelProperty(value = "登记员")
    private String djy;
}
