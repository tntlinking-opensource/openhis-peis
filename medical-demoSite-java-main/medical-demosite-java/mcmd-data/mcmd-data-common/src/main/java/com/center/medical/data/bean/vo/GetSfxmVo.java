package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建套餐-获取基础数据返回参数
 */
@Data
public class GetSfxmVo implements Serializable {
    private static final long serialVersionUID = 515815337494524414L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "收费项目名称")
    private String sfxmmc;

    @ApiModelProperty(value = "收费项目输入码")
    private String sfxmsrm;

    @ApiModelProperty(value = "性别")
    private String xb;

    @ApiModelProperty(value = "检查意义")
    private String jcyy;

    @ApiModelProperty(value = "价格")
    private String jg;

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

    @ApiModelProperty(value = "禁止打折字段，1禁止打折")
    private Integer fDiscountdisabled;

    @ApiModelProperty(value = "销售打印分类")
    private String xsdyfl;

    @ApiModelProperty(value = "打印顺序")
    private String dysx;

    @ApiModelProperty(value = "成本价")
    private Double costprice;
}
