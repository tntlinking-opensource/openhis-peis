package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 推荐收费项目-查看套餐下的检查项目返回数据
 */
@Data
public class ItemsByTcVo implements Serializable {
    private static final long serialVersionUID = 6485230867672530387L;

    @ApiModelProperty(value = "成本价")
    private Double costprice;

    @ApiModelProperty(value = "收费项目id")
    private String sfxmId;

    @ApiModelProperty(value = "收费项目名称")
    private String sfxmmc;

    @ApiModelProperty(value = "收费项目输入码")
    private String sfxmsrm;

    @ApiModelProperty(value = "性别")
    private String xb;

    @ApiModelProperty(value = "检查意义")
    private String jcyy;

    @ApiModelProperty(value = "价格")
    private Double jg;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String tjlx;

    @ApiModelProperty(value = "所属科室名称")
    private String ssks;

    @ApiModelProperty(value = "科室id")
    private String idKs;

    @ApiModelProperty(value = "检查次数")
    private String sycstj;

    @ApiModelProperty(value = "禁止打折字段，0否 1禁止")
    private Integer fDiscountdisabled;

    @ApiModelProperty(value = "销售打印分类")
    private String xsdyfl;

    @ApiModelProperty(value = "打印顺序")
    private String dysx;
}
