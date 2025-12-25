package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售提醒(SellRemind)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:26
 */
@Data
@TableName("crm_sell_remind")
@ApiModel(value = "SellRemind", description = "销售提醒实体类")
public class SellRemind extends Model<SellRemind> implements Serializable {
    private static final long serialVersionUID = 632834058912804726L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "客户id")
    private String khdwid;

    @ApiModelProperty(value = "客户名称")
    private String khdwmc;

    @ApiModelProperty(value = "提醒时间")
    private Date remindTime;

    @ApiModelProperty(value = "提醒内容")
    private String content;

    @ApiModelProperty(value = "创建人用户名")
    private String createName;

    @ApiModelProperty(value = "状态 0未结束 1 已结束")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;
}
