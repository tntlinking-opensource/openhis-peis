package com.center.medical.member.bean.vo;

import com.center.medical.bean.enums.SmsNotifyResultType;
import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-24 21:05
 * @description: 会员生日提醒数据
 */
@Data
public class MemberbirthdatVo implements Serializable {
    private static final long serialVersionUID = 5448536554368878617L;

    @ApiModelProperty(value = "ID")
    private String id;

    @Excel(name = "会员卡号")
    @ApiModelProperty(value = "会员卡号")
    private String patientcardno;



    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别" ,readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别：0.女 1.男 2.通用")
    private String idSex;

    @Excel(name = "身份证号")
    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @Excel(name = "会员级别" ,readConverterExp = "1=普通会员,2=VIP会员,3=VVIP会员")
    @ApiModelProperty(value = "会员级别")
    private String memberlevel;

    @Excel(name = "生日")
    @ApiModelProperty(value = "生日")
    private String birthdate;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private String age;

    @Excel(name = "电话")
    @ApiModelProperty(value = "电话")
    private String phone;

    @Excel(name = "分中心")
    @ApiModelProperty(value = "分中心")
    private String fzx;

    @Excel(name = "分中心")
    @ApiModelProperty(value = "单位")
    private String dw;

    @Excel(name = "回访状态" ,readConverterExp = "0=已回访,1=未回访")
    @ApiModelProperty(value = "回访状态：0已回访")
    private String visitStatus;

    @Excel(name = "回访人")
    @ApiModelProperty(value = "回访人ID")
    private String visitMan;

    @Excel(name = "回访时间")
    @ApiModelProperty(value = "回访时间")
    private String visitTime;

    @Excel(name = "回访方式")
    @ApiModelProperty(value = "回访方式")
    private String visitType;

    @Excel(name = "回访备注")
    @ApiModelProperty(value = "回访备注")
    private String visitNote;


    /**
     * @see SmsNotifyResultType
     */
    @ApiModelProperty(value = "通知结果状态：0.通知失败 1.已取消 2.等待通知 3.已通知")
    private String notifyResult;

}
