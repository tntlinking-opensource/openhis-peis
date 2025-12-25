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
 * 签单计划(CrmOrderPlan)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:44:59
 */
@Data
@TableName("crm_order_plan")
@ApiModel(value = "CrmOrderPlan", description = "签单计划实体类")
public class CrmOrderPlan extends Model<CrmOrderPlan> implements Serializable {
    private static final long serialVersionUID = 299089862543457814L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "订单ID")
    private String ddId;

    @ApiModelProperty(value = "社会信用代码")
    private String socialCreditCode;

    @ApiModelProperty(value = "年度")
    private String yearFlag;

    @ApiModelProperty(value = "签单类型ID")
    private String orderTypeId;

    @ApiModelProperty(value = "部门ID")
    private String deptId;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "业务员ID")
    private String salesId;

    @ApiModelProperty(value = "订单内容：订单名称+客户名称+客户地区信息（省市区代码地址）+体检类型+备单类型等等字段")
    private String content;

    @ApiModelProperty(value = "状态（只匹配生效中的记录）：-1.失效 0.待生效 1.生效中")
    private String status;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}
