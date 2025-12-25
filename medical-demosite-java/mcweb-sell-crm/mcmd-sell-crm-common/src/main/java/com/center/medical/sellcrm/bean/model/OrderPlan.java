package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 签单计划(OrderPlan)表实体类
 *
 * @author 路飞船长
 * @since 2023-05-16 17:38:59
 */
@Data
@TableName("crm_order_plan")
@ApiModel(value = "OrderPlan", description = "签单计划实体类")
public class OrderPlan extends Model<OrderPlan> implements Serializable {
    private static final long serialVersionUID = 995431733876874867L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "签单类型ID")
    private String orderTypeId;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "业务员ID")
    private String salesId;

    @ApiModelProperty(value = "状态（只匹配生效中的记录）：-1.失效 0.待生效 1.生效中")
    private Integer status;

    @ApiModelProperty(value = "创建日期")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "订单号")
    private String ddId;

    @ApiModelProperty(value = "年度")
    private String yearFlag;

    @ApiModelProperty(value = "部门ID")
    private String deptId;

    @ApiModelProperty(value = "订单内容：订单名称+客户名称+客户地区信息（省市区代码地址）+体检类型+备单类型等等字段")
    private String content;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "社会信用代码")
    private String socialCreditCode;
}
