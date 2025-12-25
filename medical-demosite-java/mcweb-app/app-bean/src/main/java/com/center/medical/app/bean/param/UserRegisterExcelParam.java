package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户批量导入注册参数
 *
 * @author: cl
 * @date: 2021-04-19 13:16:59
 */
@Data
public class UserRegisterExcelParam {

    @ApiModelProperty("序号")
    private String serialNo;

    @ApiModelProperty("手机号(必填)")
    private String phone;

    @ApiModelProperty("用户昵称(最多32个字)")
    private String nickName;

    @ApiModelProperty("邮箱")
    private String userMail;

    @ApiModelProperty("密码(必填)")
    private String password;

    @ApiModelProperty("性别(M(男) / F(女))")
    private String sex;

    @ApiModelProperty("生日(格式:1989-08-08)")
    private String birthDate;

    @ApiModelProperty("备注")
    private String userMemo;

    @ApiModelProperty("积分(限整数)")
    private Integer score;

    @ApiModelProperty("余额(精确小数点后两位)")
    private Double balance;

    @ApiModelProperty("成长值(限整数)")
    private Integer growth;

    @ApiModelProperty("会员类型 0普通会员 1付费会员")
    private Integer levelType;

    @ApiModelProperty("会员等级")
    private Integer level;

    @ApiModelProperty("vip结束时间")
    private Date vipEndTime;

//    @ApiModelProperty("多个标签用,隔开")
//    private String userTags;

}
