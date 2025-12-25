package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * 科室加项左侧数据，创建套餐获取基础数据收费项目返回数据
 */
@Data
public class SfxmVo implements Serializable {
    private static final long serialVersionUID = 7806821072437854283L;

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

    @ApiModelProperty(value = "体检类型")
    private String tjlx;

    @ApiModelProperty(value = "所属科室")
    private String ssks;

    @ApiModelProperty(value = "科室id")
    private String idKs;

    @ApiModelProperty(value = "检查次数")
    private String sycstj;

    @ApiModelProperty(value = "禁止打折字段，0否 1禁止")
    private String fDiscountdisabled;

    @ApiModelProperty(value = "xsdyfl")
    private String xsdyfl;

    @ApiModelProperty(value = "打印顺序")
    private String dysx;

    @ApiModelProperty(value = "成本价")
    private Double costprice;


}
