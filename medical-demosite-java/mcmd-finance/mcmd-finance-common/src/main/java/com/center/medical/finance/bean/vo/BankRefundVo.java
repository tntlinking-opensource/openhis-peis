package com.center.medical.finance.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 银行汇款结算 返回数据
 */
@Data
public class BankRefundVo implements Serializable {
    private static final long serialVersionUID = -1726213201094754014L;

    @Excel(name = "结算方式")
    @ApiModelProperty(value = "账户名称")
    private String accountName;

    @Excel(name = "客户名称")
    @ApiModelProperty(value = "客户名称")
    private String customername;

    @Excel(name = "ID号")
    @ApiModelProperty(value = "客户id：团体号/体检号/卡号一类的")
    private String idCustomer;

    @Excel(name = "金额")
    @ApiModelProperty(value = "结算金额")
    private Double moneyamountpaid;

    @Excel(name = "销售经理")
    @ApiModelProperty(value = "用户名")
    private String username;

    @Excel(name = "审核状态", readConverterExp = "null=否,0=否,1=是")
    @ApiModelProperty(value = "分检是否已审核")
    private Integer isAudit;

    @Excel(name = "同步状态", readConverterExp = "null=否,0=否,1=是")
    @ApiModelProperty(value = "是否更新")
    private String isUpdate;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String note;

}
