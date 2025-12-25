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
 * 高危人员管理表(MdRiskclient)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:20
 */
@Data
@TableName("md_riskclient")
@ApiModel(value = "MdRiskclient", description = "高危人员管理表实体类")
public class MdRiskclient extends Model<MdRiskclient> implements Serializable {
    private static final long serialVersionUID = -71593405537112273L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String tjid;

    @ApiModelProperty(value = "高危人员名称")
    private String gwrymc;

    @ApiModelProperty(value = "年龄")
    private String nl;

    @ApiModelProperty(value = "性别")
    private Integer xb;

    @ApiModelProperty(value = "联系电话")
    private String lxdh;

    @ApiModelProperty(value = "高危项目")
    private String gwxm;

    @ApiModelProperty(value = "体检类型")
    private Integer tjlx;

    @ApiModelProperty(value = "体检日期")
    private Date tirq;

    @ApiModelProperty(value = "状态：0：未处理1：处理")
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

    @ApiModelProperty(value = "同步标志  1： 已同步  ")
    private Integer tbbz;

    @ApiModelProperty(value = "处理标志 科室-危急值提报-处理  1已处理  null未处理（弃用）")
    private Integer clbz;

    @ApiModelProperty(value = "业务处理人用户名")
    private String ywclr;

    @ApiModelProperty(value = "业务处理时间")
    private Date ywclsj;

    @ApiModelProperty(value = "业务处理选择的报告发放方式id")
    private String ywfffs;

    @ApiModelProperty(value = "业务处理状态")
    private Integer ywclzt;

    @ApiModelProperty(value = "业务处理备注")
    private String ywbz;

    @ApiModelProperty(value = "回访处理人用户名")
    private String hfclr;

    @ApiModelProperty(value = "回访处理时间")
    private Date hfclsj;

    @ApiModelProperty(value = "回访处理状态，详见：com.world.center.bean.enums.Jktjzt")
    private Integer hfclzt;

    @ApiModelProperty(value = "回访处理方式")
    private String hffffs;

    @ApiModelProperty(value = "回访备注")
    private String hfbz;

    @ApiModelProperty(value = "回访发放方式")
    private Integer hfclfs;

    @ApiModelProperty(value = "专家处理人用户名")
    private String zjclr;

    @ApiModelProperty(value = "专家处理时间")
    private Date zjclsj;

    @ApiModelProperty(value = "专家处理状态 0未处理  1 已处理")
    private Integer zjclzt;

    @ApiModelProperty(value = "专家处理备注")
    private String zjbz;

    @ApiModelProperty(value = "专家处理方式")
    private Integer zjclfs;
}
