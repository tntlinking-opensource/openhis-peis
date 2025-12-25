package com.center.medical.finance.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 查看左中体检人参数
 */
@Data
public class AccountsInfoParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -4211435505315651960L;

    @ApiModelProperty(value = "含未检:0否1是")
    private Integer containUnchecked;

    @ApiModelProperty(value = "包补检:0否1是")
    private Integer containBj;

    @ApiModelProperty(value = "预定任务ID(订单id)")
    private String idOrder;

    @ApiModelProperty(value = "登记结束时间")
    private String endRegTime;

    @ApiModelProperty(value = "登记开始日期")
    private String startRegTime;

    @ApiModelProperty(value = "订单输入体检号")
    private String ddinputCode;

    @ApiModelProperty(value = "订单输入名称")
    private String ddinputName;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;

    @ApiModelProperty(value = "结账状态 0未结 1已结")
    private Integer checkoutStatus;

    @ApiModelProperty(value = "含老系统 0否1是")
    private Integer containOldSystem;

    @ApiModelProperty(value = "包含禁检 0否1是")
    private Integer containBan;
}
