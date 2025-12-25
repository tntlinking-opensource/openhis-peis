package com.center.medical.member.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 新增家庭卡办理-保存参数
 */
@Data
public class FormdataDto implements Serializable {
    private static final long serialVersionUID = 8196613747554771879L;

    @ApiModelProperty(value = "卡号")
    private String cardno;

    @ApiModelProperty(value = "剩余金额(只有家庭卡使用这个字段)")
    private Double balanceMoney;

    @ApiModelProperty(value = "会员卡剩余积分（会员卡和家庭卡使用）")
    private Double balanceJf;

    @ApiModelProperty(value = "种类名称")
    private String typeName;

    @ApiModelProperty(value = "卡前缀")
    private String cardPrefix;

    @ApiModelProperty(value = "卡标识")
    private String cardLogo;

    @ApiModelProperty(value = "有效期")
    private Date validity;

    @ApiModelProperty(value = "卡说明")
    private String cardState;

    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "售卡人ID")
    private String sellId;

    @ApiModelProperty(value = "出售时间")
    private Date sellTime;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "确认密码")
    private String passwordConfirm;

    @ApiModelProperty(value = "新增保存数据,这个里面好像缺参数")
    private ArchiveDataDto archiveData;

}
