package com.center.medical.query.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 外送项目查询 分页返回数据
 */
@Data
public class TotalSendVo implements Serializable {
    private static final long serialVersionUID = 3895170143951102736L;

    @Excel(name="外送时间",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "send0")
    private String send0;


    @Excel(name="返回时间",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "send1")
    private String send1;

    @Excel(name = "外送机构")
    @ApiModelProperty(value = "send2")
    private String send2;

    @Excel(name = "收费项目")
    @ApiModelProperty(value = "send3")
    private String send3;

    @Excel(name = "成本价")
    @ApiModelProperty(value = "成本价")
    private Double costprice;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "send4")
    private String send4;

    @Excel(name = "体检团体")
    @ApiModelProperty(value = "send5")
    private String send5;

    @Excel(name = "体检者")
    @ApiModelProperty(value = "send6")
    private String send6;

    @Excel(name = "位置")
    @ApiModelProperty(value = "send7")
    private String send7;

    @Excel(name = "性别")
    @ApiModelProperty(value = "send8")
    private String send8;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "send9")
    private String send9;


    @Excel(name="登记时间",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "send10")
    private String send10;



}
