package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取复查名单 参数
 */
@Data
public class ReviewNoticeListParam implements Serializable {
    private static final long serialVersionUID = -7029623640702705685L;


    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "时间开始")
    private Date startDate;

    @ApiModelProperty(value = "时间结束")
    private Date endDate;

    public ReviewNoticeListParam(String customerId, String ddh, Date startDate, Date endDate) {
        this.customerId = customerId;
        this.ddh = ddh;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public ReviewNoticeListParam() {
    }
}
