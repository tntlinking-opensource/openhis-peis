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
 * 订单表(Createorder)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:17:59
 */
@Data
@TableName("CREATEORDER")
@ApiModel(value = "Createorder", description = "订单表实体类")
public class Createorder extends Model<Createorder> implements Serializable {
    private static final long serialVersionUID = 575270709172756030L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "订单名称")
    private String ddmc;

    @ApiModelProperty(value = "订单代码")
    private String dddm;

    @ApiModelProperty(value = "客户单位名称ID")
    private String khdwmcid;

    @ApiModelProperty(value = "提醒方式")
    private String txfs;

    @ApiModelProperty(value = "发送短信手机号")
    private String fsdxsjh;

    @ApiModelProperty(value = "创建订单日期")
    private Date cjddrq;

    @ApiModelProperty(value = "客户单位电话")
    private String khdwdh;

    @ApiModelProperty(value = "体检中心")
    private String tjzx;

    @ApiModelProperty(value = "计划检期从")
    private Date jhjqc;

    @ApiModelProperty(value = "计划检期到")
    private Date jhjqd;

    @ApiModelProperty(value = "是否已签订合同")
    private String sfyqdht;

    @ApiModelProperty(value = "合同编号")
    private String htbh;

    @ApiModelProperty(value = "男性体检人数")
    private String nxtjrs;

    @ApiModelProperty(value = "女性体检人数")
    private String vxtjrs;

    @ApiModelProperty(value = "订单价格")
    private String ddjg;

    @ApiModelProperty(value = "订单折扣")
    private String ddzk;

    @ApiModelProperty(value = "订单优惠价")
    private String ddyhj;

    @ApiModelProperty(value = "前台须知")
    private String qtxz;

    @ApiModelProperty(value = "审批状态")
    private String spzt;

    @ApiModelProperty(value = "审批意见")
    private String spyj;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "假删状态")
    private Integer isDelete;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String urls;

    @ApiModelProperty(value = "变更状态 0:未变更 、2:已变更、3:变更已提交、4:变更审核通过")
    private Integer bgzt;

    @ApiModelProperty(value = "${column.comment}")
    private String xsdh;

    @ApiModelProperty(value = "${column.comment}")
    private Integer tjxs;

    @ApiModelProperty(value = "${column.comment}")
    private String clurls;

    @ApiModelProperty(value = "${column.comment}")
    private Integer clspzt;

    @ApiModelProperty(value = "${column.comment}")
    private String clspyj;

    @ApiModelProperty(value = "${column.comment}")
    private String idInforway;

    @ApiModelProperty(value = "${column.comment}")
    private Integer tjlx;

    @ApiModelProperty(value = "审批人")
    private String spr;

    @ApiModelProperty(value = "材料审批人")
    private String clspr;

    @ApiModelProperty(value = "${column.comment}")
    private Date submitTime;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isOnline;

    @ApiModelProperty(value = "${column.comment}")
    private Integer cantReplace;

    @ApiModelProperty(value = "${column.comment}")
    private String chestNum;

    @ApiModelProperty(value = "${column.comment}")
    private String bgmemo;

    @ApiModelProperty(value = "${column.comment}")
    private String reviewPayway;

    @ApiModelProperty(value = "${column.comment}")
    private String reviewZk;

    @ApiModelProperty(value = "${column.comment}")
    private String templateJm;

    @ApiModelProperty(value = "${column.comment}")
    private String templateText;

    @ApiModelProperty(value = "${column.comment}")
    private String kdzlName;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isMarket;
}
