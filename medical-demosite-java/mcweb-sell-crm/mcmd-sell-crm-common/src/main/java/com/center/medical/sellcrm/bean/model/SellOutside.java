package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 外出沟通(SellOutside)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:24
 */
@Data
@TableName("crm_sell_outside")
@ApiModel(value = "SellOutside", description = "外出沟通实体类")
public class SellOutside extends Model<SellOutside> implements Serializable {
    private static final long serialVersionUID = -66750534262663102L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "分中心id")
    private String fzx;

    @ApiModelProperty(value = "客户")
    private String kh;

    @ApiModelProperty(value = "总价")
    private String allprice;

    @ApiModelProperty(value = "折扣")
    private String zk;

    @ApiModelProperty(value = "实收")
    private String sj;

    @ApiModelProperty(value = "所有收费项目")
    private String items;

    @ApiModelProperty(value = "销售ID")
    private String sellman;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;
}
