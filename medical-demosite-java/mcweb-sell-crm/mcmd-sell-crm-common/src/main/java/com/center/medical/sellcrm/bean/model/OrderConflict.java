package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 撞单记录(OrderConflict)表实体类
 *
 * @author 路飞船长
 * @since 2023-04-27 18:17:30
 */
@Data
@TableName("crm_order_conflict")
@ApiModel(value = "OrderConflict", description = "撞单记录实体类")
public class OrderConflict extends Model<OrderConflict> implements Serializable {
    private static final long serialVersionUID = -64693728383036978L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID", position = 1, required = true)
    private String id;

    @ApiModelProperty(value = "订单ID(当前提交的订单)", position = 2, required = true)
    private String ddId;

    @ApiModelProperty(value = "订单相似度", position = 3, required = true)
    private String rate;

    @ApiModelProperty(value = "撞单的订单ID集合，多个以英文逗号隔开", position = 4, required = true)
    private String planIds;

    @ApiModelProperty(value = "是否撞单：0.否 1.是", position = 5, required = true)
    private Integer dealResult;

    @ApiModelProperty(value = "通过的订单ID(可能存在多个)", position = 11)
    private String passPlanId;

    @ApiModelProperty(value = "失效的订单ID(可能存在多个)", position = 12)
    private String rejectPlanId;

    @ApiModelProperty(value = "处理人ID", position = 13)
    private String dealerId;

    @ApiModelProperty(value = "处理人名称", position = 6, required = true)
    private String dealerName;

    @ApiModelProperty(value = "备注", position = 10)
    private String remark;

    @ApiModelProperty(value = "状态：0.待处理 1.已处理", position = 7, required = true)
    private Integer status;

    @ApiModelProperty(value = "处理日期", position = 8, required = true)
    private Date dealDate;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建日期", position = 9, required = true)
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改日期", position = 14)
    private Date modifydate;
}
