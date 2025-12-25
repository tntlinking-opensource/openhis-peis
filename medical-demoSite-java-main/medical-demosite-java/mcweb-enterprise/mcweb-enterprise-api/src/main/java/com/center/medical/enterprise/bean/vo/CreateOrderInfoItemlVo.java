package com.center.medical.enterprise.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 套餐详情 返回数据
 */
@Data
public class CreateOrderInfoItemlVo implements Serializable {
    private static final long serialVersionUID = 1351248887124414736L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "收费项目名称")
    private String sfxmmc;

    @ApiModelProperty(value = "性别 0男1女2通用")
    private String xb;

    @ApiModelProperty(value = "打印项目分类名称")
    private String printType;

    @ApiModelProperty(value = "价格")
    private Double jg;

    @ApiModelProperty(value = "检查意义")
    private String jcyy;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String tjlx;

    @ApiModelProperty(value = "所属科室")
    private String ssks;
}
