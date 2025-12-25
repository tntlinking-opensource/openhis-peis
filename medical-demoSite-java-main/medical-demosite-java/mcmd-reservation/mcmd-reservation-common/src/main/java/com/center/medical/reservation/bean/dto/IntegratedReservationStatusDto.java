package com.center.medical.reservation.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 新老系统集成预约接口预约状态
 * @author xhp
 * @since 2024-01-03 15:09
 */
@Data
public class IntegratedReservationStatusDto {
    @ApiModelProperty(value = "预约状态：-1预约失败,0.未预约,1.待预约, 2.已预约, 3.预约结束")
    private Integer status;
    @ApiModelProperty(value = "预约成功显示预约备注，预约失败显示失败原因")
    private String remark;
    @ApiModelProperty(value = "机构门店名称")
    private String hospitalSubName;
    @ApiModelProperty(value = "预约号")
    private String reservationNo;
    @ApiModelProperty(value = "订单类型：0.个检 1.团检")
    private String hospitalSubId;
    @ApiModelProperty(value = "机构门店ID")
    private Integer fUsecodehiden;
    @ApiModelProperty(value = "到检时间")
    private Date finishedTime;
}
