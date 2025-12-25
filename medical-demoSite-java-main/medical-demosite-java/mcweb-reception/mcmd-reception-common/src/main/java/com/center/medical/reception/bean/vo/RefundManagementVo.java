package com.center.medical.reception.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 退款管理参数
 */
@Data
public class RefundManagementVo implements Serializable {
    private static final long serialVersionUID = -2952807650961994514L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "订单号")
    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @Excel(name = "团体名称")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "操作员")
    @ApiModelProperty(value = "操作员")
    private String userName;

    @Excel(name = "结算金额")
    @ApiModelProperty(value = "结算金额")
    private Double moneyamountpaid;

    @Excel(name="注册时间",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "缴费时间")
    private Date feechargetime;

}
