package com.center.medical.sellcrm.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * 新增套餐保存Json参数
 */
@Data
public class MealJsonDto implements Serializable {
    private static final long serialVersionUID = 2493223385904316162L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "体检套餐输入码")
    private String tjtcsrm;

    @ApiModelProperty(value = "体检团体")
    private String khdwmcid;

    @ApiModelProperty(value = "体检类型")
    private Integer tjlx;

    @ApiModelProperty(value = "适用性别")
    private Integer syxb;

    @ApiModelProperty(value = "是否已婚套餐[0.是 1.否 其他.通用]")
    private String sfyhtc;

    @ApiModelProperty(value = "年龄自")
    private String nlz;

    @ApiModelProperty(value = "年龄至")
    private String nld;

    @ApiModelProperty(value = "职业体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private Integer zytjlb;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "折后价格")
    private Double zhjg;

    @ApiModelProperty(value = "套餐折扣")
    private Double tczk;

    @ApiModelProperty(value = "付款方式")
    private String fkfs;

    @ApiModelProperty(value = "分中心id")
    private String fzxid;

    @ApiModelProperty(value = "是否外出：0 .内检 1.外检")
    private Integer sfwc;

    @ApiModelProperty(value = "套餐原始价格")
    private Double tcysjg;

    @ApiModelProperty(value = "项目成本价合计")
    private Double totalCostprice;

    @ApiModelProperty(value = "平安套餐ID")
    private String pinganId;

    @ApiModelProperty(value = "是否自选套餐：0.固定套餐 1.完全自选 2.部分自选")
    private Integer sfzx;

    @ApiModelProperty(value = "自选折扣（用于APP计算自选项目价格）")
    private Double zxzk;

    @ApiModelProperty(value = "可选数量")
    private Integer kxsl;

    @ApiModelProperty(value = "分组金额")
    private Double groupPrice;

    @ApiModelProperty(value = "团惠价，优惠价不包括备选项目（app使用）")
    private Double zhjgapp;

    @ApiModelProperty(value = "是否大套餐：0或null.否 1.是")
    private String isBig;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "项目名称")
    private String xmmc;

    @ApiModelProperty(value = "项目输入码")
    private String xmsrm;
}
