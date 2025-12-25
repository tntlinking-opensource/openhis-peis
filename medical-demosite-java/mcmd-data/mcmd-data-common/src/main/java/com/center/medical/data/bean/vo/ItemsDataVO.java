package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-18 11:50
 * @description:
 */
@Data
public class ItemsDataVO implements Serializable {
    private static final long serialVersionUID = -5161207436548249065L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "收费项目名称")
    private String sfxmmc;

    @ApiModelProperty(value = "性别")
    private String xb;

    @ApiModelProperty(value = "检查意义")
    private String jcyy;

    @ApiModelProperty(value = "价格")
    private Double jg;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "体检类型")
    private String tjlx;

    @ApiModelProperty(value = "所属科室名称")
    private String ssks;

    @ApiModelProperty(value = "输入码")
    private String sfxmsrm;

    //根据套餐ID获取收费项目的字段-start
    @ApiModelProperty(value = "是否备选：0.否 1.是")
    private Integer sfbx;

    @ApiModelProperty(value = "销售打印分类")
    private String printType;

    @ApiModelProperty(value = "分组")
    private Integer itemGroup;

    @ApiModelProperty(value = "分组类型：0.组内选 1.组间选 2.组任选")
    private Integer groupType;
    //根据套餐ID获取收费项目的字段-end
}
