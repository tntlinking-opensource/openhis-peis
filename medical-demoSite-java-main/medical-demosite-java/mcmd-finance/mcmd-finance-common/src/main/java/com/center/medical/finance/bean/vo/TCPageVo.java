package com.center.medical.finance.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 团体结算 分页 返回数据
 */
@Data
public class TCPageVo implements Serializable {
    private static final long serialVersionUID = -8554442614177207550L;


    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "客户单位id")
    private String khdwid;

    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "实付金额")
    private Double ys;

    @ApiModelProperty(value = "实收金额(结算金额)")
    private Double sf;

    @ApiModelProperty("余额(精确小数点后两位)")
    private Double balance;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "销售经理")
    private String czxsjl;

    @ApiModelProperty(value = "订单id")
    private String orderId;

}
