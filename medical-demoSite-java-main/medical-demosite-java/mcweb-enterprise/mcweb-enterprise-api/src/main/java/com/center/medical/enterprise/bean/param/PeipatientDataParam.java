package com.center.medical.enterprise.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取体检者数据 参数
 */
@Data
public class PeipatientDataParam implements Serializable {
    private static final long serialVersionUID = 1434630363172602508L;

    @ApiModelProperty(value = "客户ID")
    private String customerId;

    @ApiModelProperty(value = "登记时间开始")
    private String startDate;

    @ApiModelProperty(value = "等级时间结束")
    private String endDate;

    @ApiModelProperty(value = "客户姓名")
    private String customerName;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "订单号")
    private String orderNo;


    @ApiModelProperty(value = "体检状态 0开始体检、1分拣完成、2总检完成、3报告已打印、4报告已领取")
    private String examStatus;
}
