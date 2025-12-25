package com.center.medical.finance.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 卡发放分页返回数据
 */
@Data
public class SendCardVo implements Serializable {
    private static final long serialVersionUID = -9127702634889752159L;

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "卡号")
    @ApiModelProperty(value = "卡号")
    private String cardNo;

    @Excel(name = "卡类型")
    @ApiModelProperty(value = "卡名")
    private String cardName;

    @Excel(name = "剩余金额")
    @ApiModelProperty(value = "卡内剩余的金额(会员卡、体检卡)")
    private Double balanceLimit;

    @ApiModelProperty(value = "会员卡剩余积分（会员卡和家庭卡使用）")
    private Double balanceJf;

    @Excel(name = "领取人")
    @ApiModelProperty(value = "收费员ID(弃用)")
    private String getterId;

    @Excel(name = "领取时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "领取时间")
    private Date getTime;

    @Excel(name = "有效期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "有效期")
    private Date validity;

    @Excel(name = "备注")
    @ApiModelProperty(value = "卡备注")
    private String memo;


    @ApiModelProperty(value = "创建人ID")
    private String createId;

    @Excel(name = "创建人")
    @ApiModelProperty(value = "创建人")
    private String createName;


    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @Excel(name = "密码")
    @ApiModelProperty(value = "体检卡密码")
    private String password;

    @ApiModelProperty(value = "APP用户")
    private String appUser;

    @ApiModelProperty(value = "订单名称")
    private String ddmc;

    @ApiModelProperty(value = "套餐名称")
    private String tcmc;

    @ApiModelProperty(value = "剩余金额(只有家庭卡使用这个字段)")
    private Double balanceMoney;

    @Excel(name = "初始金额")
    @ApiModelProperty(value = "面值（活动专属卡和团检专属卡即套餐优惠价，其他卡就是初始金额）")
    private Double tcPrice;

}
