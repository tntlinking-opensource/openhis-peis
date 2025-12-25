package com.center.medical.reservation.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 加密字符串解析成的对象
 */
@Data
public class ApplyForOrderDto implements Serializable {
    private static final long serialVersionUID = -3240737826432727302L;

    @ApiModelProperty(value = "体检平台订单 ID")
    private String orderId;

    @ApiModelProperty(value = "门店 ID")
    private String hospitalSubId;

    @ApiModelProperty(value = "套餐 ID")
    private String medicalPackage;

    @ApiModelProperty(value = "预约时间 yyyyMMddHHmmss")
    private String appointmentTime;

    @ApiModelProperty(value = "是否授权查看 体 检 报告：是：Y  否：N")
    private String hasAuthorized;

    @ApiModelProperty(value = "套餐展示名称")
    private String packageDisplayName;

    @ApiModelProperty(value = "是否 VIP 预约： 否：N 是：Y")
    private String isVip;

    @ApiModelProperty(value = "客户姓名")
    private String customerName;

    @ApiModelProperty(value = "客户证件类型 身份证：1，护照：2，军人证：3，港澳通行证/回乡证或台胞证：6 ")
    private String customerIdentityType;

    @ApiModelProperty(value = "客户证件号")
    private String customerIdentityNo;

    @ApiModelProperty(value = "客户性别男：M，女：F")
    private String customerGender;

    @ApiModelProperty(value = "客户出生日期 yyyyMMdd")
    private String customerBirthday;

    @ApiModelProperty(value = "生理状态： 男：01 未婚女：02 已婚女：03")
    private String medicalStatus;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "所属公司名称")
    private String companyName;


    /**
     * 微信商城
     */
    @ApiModelProperty(value = "微信商城核销码")
    private String chargedCode;


    @ApiModelProperty(value = "微信商城套餐价格")
    private String ageofreal;

}
