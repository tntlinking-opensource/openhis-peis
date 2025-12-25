package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单总结表(Ordersummary)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-22 18:38:09
 */
@Data
@TableName("md_ordersummary")
@ApiModel(value = "Ordersummary", description = "订单总结表实体类")
public class Ordersummary extends Model<Ordersummary> implements Serializable {
    private static final long serialVersionUID = -15124722765081877L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "订单ID")
    private String ddid;

    @ApiModelProperty(value = "订单名称")
    private String ddmc;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiModelProperty(value = "总结")
    private String zj;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;
}
