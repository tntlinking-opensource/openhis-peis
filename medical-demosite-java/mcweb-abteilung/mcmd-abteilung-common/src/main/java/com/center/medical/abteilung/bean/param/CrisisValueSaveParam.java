package com.center.medical.abteilung.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *  危急值提报-业务处理参数
 */
@Data
public class CrisisValueSaveParam implements Serializable {
    private static final long serialVersionUID = -8634631800666283616L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String tjid;

    @ApiModelProperty(value = "高危人员名称")
    private String gwrymc;

    @ApiModelProperty(value = "性别")
    private String xb;

    @ApiModelProperty(value = "年龄")
    private String nl;

    @ApiModelProperty(value = "联系电话")
    private String lxdh;

    @ApiModelProperty(value = "体检类型")
    private String tjlx;

    @ApiModelProperty(value = "体检日期")
    private Date tirq;

    @ApiModelProperty(value = "开单医生")
    private String kdys;

    @ApiModelProperty(value = "体检结果")
    private String wjzxj;

    @ApiModelProperty(value = "业务发放方式")
    private String ywfffs;

    @ApiModelProperty(value = "业务处理状态")
    private String ywclzt;

    @ApiModelProperty(value = "业务备注")
    private String ywbz;

    @ApiModelProperty(value = "业务处理人")
    private String ywclr;

    @ApiModelProperty(value = "回访处理状态")
    private String hfclzt;

    @ApiModelProperty(value = "回访处理方式")
    private String hfclfs;

    @ApiModelProperty(value = "回访备注")
    private String hfbz;

    @ApiModelProperty(value = "回访发放方式")
    private String hffffs;

    @ApiModelProperty(value = "回访处理人")
    private String hfclr;



    @ApiModelProperty(value = "专家处理状态")
    private String zjclzt;

    @ApiModelProperty(value = "专家处理方式")
    private String zjclfs;

    @ApiModelProperty(value = "专家备注")
    private String zjbz;

    @ApiModelProperty(value = "专家处理人")
    private String zjclr;
}
