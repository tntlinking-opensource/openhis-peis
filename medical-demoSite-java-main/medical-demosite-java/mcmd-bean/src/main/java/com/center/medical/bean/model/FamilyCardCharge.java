package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 家庭卡充值记录(FamilyCardCharge)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:53:22
 */
@Data
@TableName("md_family_card_charge")
@ApiModel(value = "FamilyCardCharge", description = "家庭卡充值记录实体类")
public class FamilyCardCharge extends Model<FamilyCardCharge> implements Serializable {
    private static final long serialVersionUID = 712421388320160791L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "卡号")
    private String cardno;

    @ApiModelProperty(value = "操作人用户名")
    private String chargerUsername;

    @ApiModelProperty(value = "充值/消费时间")
    private Date chargeTime;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "付款方式id")
    private String idPayway;

    @ApiModelProperty(value = "金额")
    private Double money;

    @ApiModelProperty(value = "操作类型：0.充值 1.消费 2.退费")
    private Integer type;

    @ApiModelProperty(value = "积分(充值正、 消费负)")
    private Double jf;

    @ApiModelProperty(value = "充值前积分")
    private Double startJf;

    @ApiModelProperty(value = "充值后积分")
    private Double endJf;

    @ApiModelProperty(value = "充值前金额")
    private Double startMoney;

    @ApiModelProperty(value = "充值后金额")
    private Double endMoney;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "消费类型，详见：CardConsumeType")
    private String consumetype;
}
