package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检团体结算表(GroupBalance)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:00
 */
@Data
@TableName("md_group_balance")
@ApiModel(value = "GroupBalance", description = "体检团体结算表实体类")
public class GroupBalance extends Model<GroupBalance> implements Serializable {
    private static final long serialVersionUID = -23275883299531580L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "收费方式ID(弃用)")
    private String typeId;

    @ApiModelProperty(value = "实收金额(结算金额)")
    private Double realityMoney;

    @ApiModelProperty(value = "是预缴金(待用)")
    private Double prepaidAmount;

    @ApiModelProperty(value = "收费员ID(弃用)")
    private String getterId;

    @ApiModelProperty(value = "收费时间(弃用)")
    private Date getterTime;

    @ApiModelProperty(value = "备注(弃用)")
    private String memo;

    @ApiModelProperty(value = "是否已结清：0或null.否 1.是")
    private Integer isSquare;

    @ApiModelProperty(value = "退费金额(弃用)")
    private Double returnMoney;

    @ApiModelProperty(value = "团体ID")
    private String groupId;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "应收金额 (弃用：应该是动态计算所有统收已收的金额)")
    private Double shouldMoney;

    @ApiModelProperty(value = "卡ID(弃用)")
    private String cardId;
}
