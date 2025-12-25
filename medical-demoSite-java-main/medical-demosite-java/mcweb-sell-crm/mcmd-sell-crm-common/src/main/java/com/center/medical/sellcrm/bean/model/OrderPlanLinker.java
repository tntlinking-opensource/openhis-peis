package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 签单计划联系人(OrderPlanLinker)表实体类
 *
 * @author 路飞船长
 * @since 2023-04-28 11:36:51
 */
@Data
@TableName("crm_order_plan_linker")
@ApiModel(value = "OrderPlanLinker", description = "签单计划联系人实体类")
public class OrderPlanLinker extends Model<OrderPlanLinker> implements Serializable {
    private static final long serialVersionUID = 900180784534794640L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "计划ID")
    private String planId;

    @ApiModelProperty(value = "姓名")
    private String linker;

    @ApiModelProperty(value = "职务")
    private String post;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "电子邮件")
    private String email;

    @ApiModelProperty(value = "个人爱好")
    private String hobby;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建日期")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;
}
