package com.center.medical.datamove.oracle.bean.model;


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
 * 普通套餐表(Createmeal)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:17:57
 */
@Data
@TableName("CREATEMEAL")
@ApiModel(value = "Createmeal", description = "普通套餐表实体类")
public class Createmeal extends Model<Createmeal> implements Serializable {
    private static final long serialVersionUID = 421834051957227348L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "体检套餐输入码")
    private String tjtcsrm;

    @ApiModelProperty(value = "体检套餐简称")
    private String tjtcjc;

    @ApiModelProperty(value = "体检类型")
    private Integer tjlx;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "适用性别")
    private Integer syxb;

    @ApiModelProperty(value = "套餐原始价格")
    private Double tcysjg;

    @ApiModelProperty(value = "套餐折扣")
    private Double tczk;

    @ApiModelProperty(value = "折后价格")
    private Double zhjg;

    @ApiModelProperty(value = "客户单位名称ID")
    private String khdwmcid;

    @ApiModelProperty(value = "是否已备单")
    private String sfybd;

    @ApiModelProperty(value = "是否已婚套餐")
    private String sfyhtc;

    @ApiModelProperty(value = "年龄自")
    private String nlz;

    @ApiModelProperty(value = "年龄至")
    private String nld;

    @ApiModelProperty(value = "付款方式")
    private String fkfs;

    @ApiModelProperty(value = "职业体检类别")
    private Integer zytjlb;

    @ApiModelProperty(value = "0:标示普通套餐")
    private String combostate;

    @ApiModelProperty(value = "可选数量")
    private String kxsl;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "分中心ID--存在多个分中心")
    private String fzxid;

    @ApiModelProperty(value = "假删状态")
    private Integer isDelete;

    @ApiModelProperty(value = "编辑状态 (0：可编辑/1：不可编辑)")
    private Integer bjzt;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "团检加项、弃项用，0：未修改 1：已用于加项弃项")
    private Integer isOrgPlus;

    @ApiModelProperty(value = "${column.comment}")
    private Integer zkztw;

    @ApiModelProperty(value = "${column.comment}")
    private Double tjm;

    @ApiModelProperty(value = "${column.comment}")
    private Integer sfzx;

    @ApiModelProperty(value = "${column.comment}")
    private Integer sfwc;

    @ApiModelProperty(value = "${column.comment}")
    private Integer forbidden;

    @ApiModelProperty(value = "${column.comment}")
    private Double zxzk;

    @ApiModelProperty(value = "${column.comment}")
    private String pinganId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isBig;

    @ApiModelProperty(value = "${column.comment}")
    private String tongLimit;

    @ApiModelProperty(value = "${column.comment}")
    private Double zhjgapp;

    @ApiModelProperty(value = "${column.comment}")
    private Double groupPrice;

    @ApiModelProperty(value = "${column.comment}")
    private Double totalCostprice;

    @ApiModelProperty(value = "${column.comment}")
    private String bz;
}
