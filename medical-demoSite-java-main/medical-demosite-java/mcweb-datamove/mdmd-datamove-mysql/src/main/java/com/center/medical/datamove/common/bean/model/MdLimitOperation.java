package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * CW卡额度操作表(MdLimitOperation)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:25
 */
@Data
@TableName("md_limit_operation")
@ApiModel(value = "MdLimitOperation", description = "CW卡额度操作表实体类")
public class MdLimitOperation extends Model<MdLimitOperation> implements Serializable {
    private static final long serialVersionUID = 396623560415378970L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检卡ID")
    private String cardId;

    @ApiModelProperty(value = "卡类型")
    private String cardType;

    @ApiModelProperty(value = "记录增加或减少的金额或者积分")
    private Double limit;

    @ApiModelProperty(value = "备注")
    private String memotext;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "操作人ID")
    private String operationId;

    @ApiModelProperty(value = "是否为增加：0.为充值 1.为消费 2.退费")
    private Integer isAdd;

    @ApiModelProperty(value = "操作日期")
    private Date operationTime;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;

    @ApiModelProperty(value = "付款方式ID")
    private String payMode;

    @ApiModelProperty(value = "操作后金额")
    private Double handleMoney;

    @ApiModelProperty(value = "分中心ID")
    private String branchCenter;

    @ApiModelProperty(value = "体检号")
    private String chargeId;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "会员卡号")
    private String memberCardno;

    @ApiModelProperty(value = "消费类型，详见：com.world.center.bean.enums.FamilyCardConsumeType")
    private String consumetype;
}
