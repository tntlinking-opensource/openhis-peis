package com.center.medical.enterprise.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 套餐列表 返回数据
 */
@Data
public class CreateOrderInfoMealVo implements Serializable {
    private static final long serialVersionUID = 1351248887024413736L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "pId,都是null不知道干什么用的")
    private String pId;

    @ApiModelProperty(value = "套餐状态--用于区分从哪张表中取套餐(0:普通套餐、1/2:最小套餐)")
    private String combostate;

    @ApiModelProperty(value = "体检系统套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String tjlx;

    @ApiModelProperty(value = "适用性别")
    private String syxb;

    @ApiModelProperty(value = "折后价格")
    private Double zhjg;

    @ApiModelProperty(value = "套餐原始价格")
    private Double tcysjg;

    @ApiModelProperty(value = "套餐折扣")
    private Double tczk;

    @ApiModelProperty(value = "付款方式")
    private String fkfs;

    @ApiModelProperty(value = "是否已婚套餐")
    private String sfyhtc;

    @ApiModelProperty(value = "年龄自")
    private String nlz;

    @ApiModelProperty(value = "年龄至")
    private String nld;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "体检套餐简称")
    private String tjtcjc;

    @ApiModelProperty(value = "职业体检类别")
    private String zytjlb;
}
