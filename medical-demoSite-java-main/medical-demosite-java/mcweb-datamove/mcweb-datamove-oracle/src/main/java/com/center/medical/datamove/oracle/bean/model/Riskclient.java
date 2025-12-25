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
 * 高危人员管理表(Riskclient)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:26
 */
@Data
@TableName("RISKCLIENT")
@ApiModel(value = "Riskclient", description = "高危人员管理表实体类")
public class Riskclient extends Model<Riskclient> implements Serializable {
    private static final long serialVersionUID = -33939105647726044L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String tjid;

    @ApiModelProperty(value = "高危人员名称")
    private String gwrymc;

    @ApiModelProperty(value = "年龄")
    private String nl;

    @ApiModelProperty(value = "性别")
    private String xb;

    @ApiModelProperty(value = "联系电话")
    private String lxdh;

    @ApiModelProperty(value = "高危项目")
    private String gwxm;

    @ApiModelProperty(value = "体检类型")
    private String tjlx;

    @ApiModelProperty(value = "体检日期")
    private Date tirq;

    @ApiModelProperty(value = "0：未处理 1：处理")
    private Integer tjzt;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "分中心ID")
    private String cid;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "销售经理（开单医生ID）")
    private String idOpendoctor;

    @ApiModelProperty(value = "提报者")
    private String reportMan;

    @ApiModelProperty(value = "提报科室")
    private String reportDivision;

    @ApiModelProperty(value = "处理人")
    private String dealMan;

    @ApiModelProperty(value = "提报状态")
    private Integer reportstatus;

    @ApiModelProperty(value = "提报时间")
    private Date reportTime;

    @ApiModelProperty(value = "处理时间")
    private Date dealTime;

    @ApiModelProperty(value = "${column.comment}")
    private Integer tbbz;

    @ApiModelProperty(value = "${column.comment}")
    private Integer clbz;

    @ApiModelProperty(value = "${column.comment}")
    private String ywclr;

    @ApiModelProperty(value = "${column.comment}")
    private Date ywclsj;

    @ApiModelProperty(value = "${column.comment}")
    private String ywfffs;

    @ApiModelProperty(value = "${column.comment}")
    private String hfclr;

    @ApiModelProperty(value = "${column.comment}")
    private Date hfclsj;

    @ApiModelProperty(value = "${column.comment}")
    private Integer hfclzt;

    @ApiModelProperty(value = "${column.comment}")
    private String hffffs;

    @ApiModelProperty(value = "${column.comment}")
    private String hfbz;

    @ApiModelProperty(value = "${column.comment}")
    private Integer hfclfs;

    @ApiModelProperty(value = "${column.comment}")
    private String zjclr;

    @ApiModelProperty(value = "${column.comment}")
    private Date zjclsj;

    @ApiModelProperty(value = "${column.comment}")
    private Integer zjclzt;

    @ApiModelProperty(value = "${column.comment}")
    private String zjbz;

    @ApiModelProperty(value = "${column.comment}")
    private Integer zjclfs;

    @ApiModelProperty(value = "${column.comment}")
    private String ywbz;

    @ApiModelProperty(value = "${column.comment}")
    private Double ywclzt;
}
