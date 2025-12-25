package com.center.medical.finance.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 财务提报分页参数
 */
@Data
public class FinancialReportingParam implements Serializable {
    private static final long serialVersionUID = 225528265120141425L;

    @ApiModelProperty(value = "分中心id")
    private String cid;

    @ApiModelProperty(value = "选择时间，请选择提报月份，带着时分秒0点0分0秒")
    private Date choosedate;

}
