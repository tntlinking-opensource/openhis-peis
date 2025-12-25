package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-15 17:20
 * @description: 套餐关联的收费项目
 */
@Data
public class ComboAndItemVo implements Serializable {
    private static final long serialVersionUID = -3905639537117901325L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "收费项目ID")
    private String sfxmid;

    @NotNull(message = "请填写是否必检")
    @ApiModelProperty(value = "是否必检：0.选检 1.必检 ", required = true)
    private Integer sfbj;

    @ApiModelProperty(value = "收费项目名称")
    private String sfxmmc;

    @ApiModelProperty(value = "收费项目输入码")
    private String sfxmsrm;

    @ApiModelProperty(value = "性别 0男1女其他通用")
    private String xb;

    @ApiModelProperty(value = "检查意义")
    private String jcyy;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "体检类型 0健康1职业其他综合")
    private String tjlx;

    @ApiModelProperty(value = "所属科室名称")
    private String ssks;

    @ApiModelProperty(value = "价格")
    private Double jg;

    @ApiModelProperty(value = "状态标识")
    private String _state;


    @ApiModelProperty(value = "xmzt")
    private String xmzt;


    @ApiModelProperty(value = "打印项目分类名称")
    private String printType;

    @ApiModelProperty(value = "顺序 实际使用此字段排序")
    private Integer printShunxu;
}
