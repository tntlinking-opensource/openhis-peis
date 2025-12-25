package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * CW卡额度操作表(LimitOperation)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:06
 */
@Data
@TableName("md_limit_operation")
@ApiModel(value = "LimitOperation", description = "CW卡额度操作表实体类")
public class LimitOperation extends Model<LimitOperation> implements Serializable {
    private static final long serialVersionUID = -50535403025052203L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检卡号")
    private String cardId;

    @ApiModelProperty(value = "卡类型")
    private String cardType;

    @TableField(value = "`limit`")
    @ApiModelProperty(value = "记录增加或减少的金额或者积分：大于0增加、小于0减少")
    private Double limit;

    @ApiModelProperty(value = "备注")
    private String memotext;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "操作人ID")
    private String operationId;

    @ApiModelProperty(value = "卡操作：0.为充值 1.为消费 2.退费")
    private Integer isAdd;

    @ApiModelProperty(value = "操作日期")
    private Date operationTime;

    @TableField(fill = FieldFill.UPDATE)
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

    @ApiModelProperty(value = "消费类型，详见：CardConsumeType")
    private String consumetype;

    public LimitOperation() {
    }

    public LimitOperation(String cardId, String cardType, Double limit, String memotext, Date operationTime,
                          String operationId, Integer isAdd, String payMode, Double handleMoney, String branchCenter, String chargeId,
                          Integer isDelete, String patientname, String memberCardno) {
        super();
        this.cardId = cardId;
        this.cardType = cardType;
        this.limit = limit;
        this.memotext = memotext;
        this.operationTime = operationTime;
        this.operationId = operationId;
        this.isAdd = isAdd;
        this.payMode = payMode;
        this.handleMoney = handleMoney;
        this.branchCenter = branchCenter;
        this.chargeId = chargeId;
        this.isDelete = isDelete;
        this.patientname = patientname;
        this.memberCardno = memberCardno;
    }
}
