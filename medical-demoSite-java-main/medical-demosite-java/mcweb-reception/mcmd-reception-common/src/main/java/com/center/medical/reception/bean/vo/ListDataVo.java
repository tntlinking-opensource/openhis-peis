package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 收费项目下拉返回数据
 */

@Data
public class ListDataVo implements Serializable {
    private static final long serialVersionUID = -5146137098543348322L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "收费检查项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "输入码")
    private String sfxmsrm;

    @ApiModelProperty(value = "导引单分组")
    private String dydfz;

    @ApiModelProperty(value = "体检类型")
    private String tjlx;

    @ApiModelProperty(value = "导引单打印标示")
    private String dyddybs;

    @ApiModelProperty(value = "部门名称")
    private String departName;

    @ApiModelProperty(value = "餐序")
    private String cx;

    @ApiModelProperty(value = "性别")
    private String xb;

    @ApiModelProperty(value = "价格")
    private Double unitprice;

    @ApiModelProperty(value = "销售打印分类")
    private String xsdyfl;

    @ApiModelProperty(value = "外送机构ID")
    private String idCooporg;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "是否在APP出现：0或null.否 1.是")
    private String examfeeitemCodex;

    @ApiModelProperty(value = "折扣")
    private Double zk;

    @ApiModelProperty(value = "套餐价格")
    private Double suiteprice;

    @ApiModelProperty(value = "特需价格")
    private Double txjg;

    @ApiModelProperty(value = "外宾价格")
    private Double wbjg;

    @ApiModelProperty(value = "优待价格")
    private Double ydjg;

    @ApiModelProperty(value = "内部价")
    private Double nbj;

    @ApiModelProperty(value = "耗材价格")
    private Double materialprice;

    @ApiModelProperty(value = "成本价")
    private Double costprice;

    @ApiModelProperty(value = "外部价")
    private Double coopprice;

    @ApiModelProperty(value = "yblx")
    private String yblx;

    @ApiModelProperty(value = "dlbs")
    private String dlbs;

    @ApiModelProperty(value = "费用类型")
    private String fylx;

    @ApiModelProperty(value = "收费项目打印名称")
    private String examfeeitemNameprn;

    @ApiModelProperty(value = "隐私项目：0.不是 1.是")
    private String bgdybs;

    @ApiModelProperty(value = "序号")
    private Integer xh;

    @ApiModelProperty(value = "标示")
    private String bs;

    @ApiModelProperty(value = "检查次数")
    private Integer jccs;

    @ApiModelProperty(value = "检查意义")
    private String jcyy;

    @ApiModelProperty(value = "所属科室ID")
    private String idDepart;

    @ApiModelProperty(value = "是否禁用：0.否 1.是")
    private Integer isBan;
}
