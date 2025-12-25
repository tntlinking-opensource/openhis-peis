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
 * 签单计划联系人(CrmOrderPlanLinker)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:00
 */
@Data
@TableName("crm_order_plan_linker")
@ApiModel(value = "CrmOrderPlanLinker", description = "签单计划联系人实体类")
public class CrmOrderPlanLinker extends Model<CrmOrderPlanLinker> implements Serializable {
    private static final long serialVersionUID = 333349877255716269L;

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
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}
