package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询基础数据收费项目返回数据
 */
@Data
public class SfxmDataVo implements Serializable {
    private static final long serialVersionUID = -4226403330697898281L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "收费项目名称")
    private String sfxmmc;

    @ApiModelProperty(value = "收费项目输入码")
    private String sfxmsrm;

    @ApiModelProperty(value = "性别0男、1女、其他是通用")
    private Integer xb;

    @ApiModelProperty(value = "检查意义")
    private String jcyy;

    @ApiModelProperty(value = "价格")
    private Double jg;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private Integer tjlx;

    @ApiModelProperty(value = "所属科室名称")
    private String ssks;

    @ApiModelProperty(value = "科室id")
    private String idKs;

    @ApiModelProperty(value = "检查次数")
    private Integer sycstj;


}
