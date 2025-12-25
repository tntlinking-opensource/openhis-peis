package com.center.medical.reservation.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 申请预约平安 返回数据
 */
@Data
public class ApplyForOrderVo implements Serializable {
    private static final long serialVersionUID = -1840534972112353733L;

    /**
     * 正常：200，参数异常 400，
     * 权限异常: 401
     * 未知错误: 500
     * 不能预约: 600
     * 预约人数已满: 601
     * 其他无法预约情况: 602
     */
    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "医疗机构订单 ID")
    private String hospitalOrderId;

    @ApiModelProperty(value = "预约订单状态：预约已确认：01 预约待确认：02")
    private String orderState;

}
