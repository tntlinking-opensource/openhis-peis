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
 * 外出沟通(CrmSellOutside)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:00
 */
@Data
@TableName("crm_sell_outside")
@ApiModel(value = "CrmSellOutside", description = "外出沟通实体类")
public class CrmSellOutside extends Model<CrmSellOutside> implements Serializable {
    private static final long serialVersionUID = 977605831729382400L;

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
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
