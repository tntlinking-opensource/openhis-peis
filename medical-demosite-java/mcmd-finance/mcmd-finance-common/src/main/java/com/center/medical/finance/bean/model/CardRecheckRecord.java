package com.center.medical.finance.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 十周年卡复查金额记录表(CardRecheckRecord)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:56
 */
@Data
@TableName("md_card_recheck_record")
@ApiModel(value = "CardRecheckRecord", description = "十周年卡复查金额记录表实体类")
public class CardRecheckRecord extends Model<CardRecheckRecord> implements Serializable {
    private static final long serialVersionUID = 156760344837260395L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "操作起始金额")
    private Double startMoney;

    @ApiModelProperty(value = "操作结束金额")
    private Double endMoney;

    @ApiModelProperty(value = "金额(消费负数，充值正数)")
    private Double money;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "卡号")
    private String cardNo;

    @ApiModelProperty(value = "体检号（消费）")
    private String patientcode;

    @ApiModelProperty(value = "操作时间")
    private Date chargeTime;

    @ApiModelProperty(value = "操作类型：0.消费 1.充值")
    private Integer chargeType;

    @ApiModelProperty(value = "操作人用户名")
    private String username;


    public CardRecheckRecord() {
        super();
    }

    public CardRecheckRecord(Double startMoney, Double endMoney, Double money, String note, String patientcode,
                             String cardNo, Date chargeTime, Integer chargeType, String username) {
        super();
        this.startMoney = startMoney;
        this.endMoney = endMoney;
        this.money = money;
        this.note = note;
        this.patientcode = patientcode;
        this.cardNo = cardNo;
        this.chargeTime = chargeTime;
        this.chargeType = chargeType;
        this.username = username;
    }
}
