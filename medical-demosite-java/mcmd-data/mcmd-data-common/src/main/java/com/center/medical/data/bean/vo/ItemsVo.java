package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-22 10:08
 * @description: 收费项目数据
 */
@Data
public class ItemsVo implements Serializable {
    private static final long serialVersionUID = -4188210850361845660L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "收费项目输入码")
    private String sfxmsrm;

    @ApiModelProperty(value = "性别")
    private String xb;

    @ApiModelProperty(value = "检查意义")
    private String jcyy;

    @ApiModelProperty(value = "价格")
    private Double unitprice;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String tjlx;

    @ApiModelProperty(value = "所属科室名称")
    private String departName;

    @ApiModelProperty(value = "所属科室ID")
    private String idDepart;

    @ApiModelProperty(value = "禁止打折字段，0否 1禁止")
    private Integer fDiscountdisabled;

    @ApiModelProperty(value = "是否必检：0.选检 1.必检 ")
    private Integer sfbj;

    @ApiModelProperty(value = "成本价格")
    private Double costprice;
}
