package com.center.medical.bean.param;

import com.center.medical.common.utils.page.PageParamSimple;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检者查询筛选条件
 * @author: 路飞船长
 * @date: 2024/4/23 09:27
 * @description: 体检者查询筛选条件
 */
@Data
public class OrderUserParam implements Serializable {
    private static final long serialVersionUID = -8255464729706385763L;

    @ApiModelProperty(value = "订单ID", required = true)
    private String orderId;

    @ApiModelProperty(value = "分页参数", required = true)
    private PageParamSimple pageParam;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "登记起始时间")
    private Date startTime;

    @ApiModelProperty(value = "登记结束时间")
    private Date endTime;

    @ApiModelProperty(value = "是否来检：0.未来检 1.已来检")
    private Integer registered;

    @ApiModelProperty(value = "报告是否已出：0.未出 1.已出")
    private Integer hadReport;

    @ApiModelProperty(value = "报告是否领取：0.未领取 1.已领取")
    private Integer hadToked;

    @ApiModelProperty(value = "是否预约：0.未预约 1.已预约")
    private Integer hadReservation;
}
