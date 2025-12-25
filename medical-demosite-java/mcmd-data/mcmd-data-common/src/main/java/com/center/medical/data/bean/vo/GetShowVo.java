package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回符合条件的收费项目数据返回数据
 */
@Data
public class GetShowVo implements Serializable {
    private static final long serialVersionUID = 3876515837631361820L;

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

    @ApiModelProperty(value = "收费科室")
    private String ssks;


}
