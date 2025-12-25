package com.center.medical.reservation.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取可预约vip人数 返回数据
 */
@Data
public class ReVipNumberVo implements Serializable {
    private static final long serialVersionUID = -5497086551327520424L;

    @ApiModelProperty(value = "预约日期")
    private Date reservationDate;


    @ApiModelProperty(value = "总人数")
    private String totalNum;


    @ApiModelProperty(value = "已预约人数")
    private String reservationNum;

}
