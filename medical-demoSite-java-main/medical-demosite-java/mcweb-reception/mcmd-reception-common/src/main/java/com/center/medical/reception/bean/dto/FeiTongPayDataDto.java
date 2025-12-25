package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 当日团体非统收汇总sql
 */
@Data
public class FeiTongPayDataDto implements Serializable {
    private static final long serialVersionUID = -8377472055085682968L;

    @ApiModelProperty(value = "缴费时间")
    private Date feechargetime;

    @ApiModelProperty(value = "数字形式团体ID，方便财务导出后处理数据使用")
    private String intId;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "实付金额")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "用户名")
    private String username;
}
