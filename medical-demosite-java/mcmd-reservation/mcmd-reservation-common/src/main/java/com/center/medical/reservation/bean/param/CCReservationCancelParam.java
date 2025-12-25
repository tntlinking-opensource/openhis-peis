package com.center.medical.reservation.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 合作单位开放接口预约取消参数
 * @author: 路飞
 * @date: 2024-11-12 14:46
 * @description: 合作单位开放接口预约取消参数
 */
@Data
@ApiModel(value = "合作单位开放接口预约取消参数")
public class    CCReservationCancelParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = 747380658339427693L;

    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "预约号")
    private String reservationNo;

    @ApiModelProperty(value = "预约失败的原因")
    private String failReason;

}
