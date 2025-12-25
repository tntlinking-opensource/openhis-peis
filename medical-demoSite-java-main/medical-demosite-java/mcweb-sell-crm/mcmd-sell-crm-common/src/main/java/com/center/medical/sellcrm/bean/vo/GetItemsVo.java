package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取收费项目返回参数
 */
@Data
public class GetItemsVo implements Serializable {
    private static final long serialVersionUID = 3858676061685226540L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "排序")
    private String sort;

    @ApiModelProperty(value = "收费项目id")
    private String sfxmid;

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

    @ApiModelProperty(value = "所属科室")
    private String ssks;

    @ApiModelProperty(value = "是否必检：0.选检 1.必检 ")
    private Integer sfbj;

}
