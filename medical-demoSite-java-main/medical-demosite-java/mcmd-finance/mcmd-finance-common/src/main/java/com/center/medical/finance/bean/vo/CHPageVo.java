package com.center.medical.finance.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检卡办理分页返回数据
 */
@Data
public class CHPageVo implements Serializable {
    private static final long serialVersionUID = 3705252828808379955L;

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "售卡人")
    @ApiModelProperty(value = "售卡人姓名")
    private String seller;

    @Excel(name = "售卡时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "售卡时间")
    private Date sellTime;

    @Excel(name = "卡号")
    @ApiModelProperty(value = "卡号")
    private String cardNo;

    @Excel(name = "面值")
    @ApiModelProperty(value = "套餐价格")
    private Double tcprice;

    @Excel(name = "卡类型")
    @ApiModelProperty(value = "卡类型")
    private String typeName;

    @Excel(name = "是否已售", readConverterExp = "0=否,1=是,null=否")
    @ApiModelProperty(value = "销售状态：0或null未售 1.已售")
    private Integer sellStatus;

    @Excel(name = "是否已用", readConverterExp = "0=否,1=是,null=否")
    @ApiModelProperty(value = "赠送套餐是否已用：0或null.否 1.是")
    private Integer tcChecked;

    @Excel(name = "使用时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "使用套餐完成登记的时间")
    private Date tcDateregister;

    @Excel(name = "使用体检号")
    @ApiModelProperty(value = "使用赠送套餐完成登记的体检号")
    private String tcPatientcode;

    @Excel(name = "售价")
    @ApiModelProperty(value = "售价")
    private Double sellPrice;

    @Excel(name = "购卡人")
    @ApiModelProperty(value = "购卡人名字（不一定在系统中）")
    private String purchaser;

    @Excel(name = "购卡人手机号")
    @ApiModelProperty(value = "电话")
    private String phone;

    @Excel(name = "卡前缀")
    @ApiModelProperty(value = "卡前缀")
    private String cardPrefix;

    @Excel(name = "卡标识")
    @ApiModelProperty(value = "卡标识")
    private String cardLogo;

    @Excel(name = "有效期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "有效期")
    private Date validity;

    @Excel(name = "卡说明")
    @ApiModelProperty(value = "卡说明")
    private String cardState;

    @Excel(name = "套餐")
    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;

    @Excel(name = "备注")
    @ApiModelProperty(value = "卡备注")
    private String memo;


}
