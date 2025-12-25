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
 * 撞单记录(CrmOrderConflict)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:44:59
 */
@Data
@TableName("crm_order_conflict")
@ApiModel(value = "CrmOrderConflict", description = "撞单记录实体类")
public class CrmOrderConflict extends Model<CrmOrderConflict> implements Serializable {
    private static final long serialVersionUID = 942392685986011116L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "订单ID")
    private String ddId;

    @ApiModelProperty(value = "订单相似度")
    private String rate;

    @ApiModelProperty(value = "撞单的订单ID集合，多个以英文逗号隔开")
    private String planIds;

    @ApiModelProperty(value = "是否撞单：0.否 1.是")
    private String dealResult;

    @ApiModelProperty(value = "通过的订单ID(可能存在多个)")
    private String passPlanId;

    @ApiModelProperty(value = "失效的订单ID(可能存在多个)")
    private String rejectPlanId;

    @ApiModelProperty(value = "处理人ID")
    private String dealerId;

    @ApiModelProperty(value = "处理人名称")
    private String dealerName;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "分中心ID")
    private Integer baranchId;

    @ApiModelProperty(value = "状态：0.待处理 1.已处理")
    private String status;

    @ApiModelProperty(value = "处理日期")
    private Date dealDate;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}
