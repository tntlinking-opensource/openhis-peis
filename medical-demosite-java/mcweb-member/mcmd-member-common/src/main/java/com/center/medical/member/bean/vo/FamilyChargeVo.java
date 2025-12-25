package com.center.medical.member.bean.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 家庭卡消费记录分页返回数据
 */
@Data
public class FamilyChargeVo implements Serializable {
    private static final long serialVersionUID = -4818491860042469636L;

    @Excel(name = "序号")
    @TableField(exist = false)
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "卡号")
    @ApiModelProperty(value = "卡号")
    private String patientcardno;

    @Excel(name = "类型" ,readConverterExp = "0=充值,1=消费,2=充值")
    @ApiModelProperty(value = "类型")
    private String type;

    @Excel(name = "充值/消费时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "充值/消费时间")
    private Date chargeTime;

    @Excel(name = "充值/消费金额")
    @ApiModelProperty(value = "金额")
    private String money;

    @Excel(name = "充值/消费积分")
    @ApiModelProperty(value = "积分")
    private Double jf;

    @Excel(name = "充值/消费方式")
    @ApiModelProperty(value = "支付方式名称")
    private String paywayName;

    @Excel(name = "办卡人")
    @ApiModelProperty(value = "办卡人")
    private String bkr;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "持卡人关系" ,readConverterExp = "0=其他家庭成员,1=主持卡人,null=其他家庭成员")
    @ApiModelProperty(value = "持卡人关系  0或null其他家庭成员,1=主持卡人")
    private Integer isMain;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "余额")
    @ApiModelProperty(value = "充值后金额")
    private Double endMoney;

    @Excel(name = "剩余积分")
    @ApiModelProperty(value = "充值后积分")
    private Double endJf;

    @Excel(name = "持卡人关系" ,readConverterExp = "0=体检,1=药房,2=口腔科,3=眼镜店,4=合作,5=保健品")
    @ApiModelProperty(value = "消费类型，0=体检,1=药房,2=口腔科,3=眼镜店,4=合作,5=保健品")
    private String consumetype;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String kbz;

    @ApiModelProperty(value = "剩余金额(只有家庭卡使用这个字段)")
    private Double balanceMoney;


}
