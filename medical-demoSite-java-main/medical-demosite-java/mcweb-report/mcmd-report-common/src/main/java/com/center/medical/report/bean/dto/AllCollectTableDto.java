package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 本次职业健康检查危害因素人员检查情况汇总一览表
 */
@Data
public class AllCollectTableDto implements Serializable {
    private static final long serialVersionUID = 2249441857747050488L;

    @ApiModelProperty(value = "职业体检类别(工作状态名称)")
    private String regimentationNote;

    @ApiModelProperty(value = "危害因素名称")
    private String harmName;

    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @ApiModelProperty(value = "疑似职业病人数")
    private Integer yszyb;

    @ApiModelProperty(value = "职业禁忌症人数")
    private Integer zyjjz;

    @ApiModelProperty(value = "复查人数")
    private Integer fc;

    @ApiModelProperty(value = "其他疾病或异常人数")
    private Integer qtjb;

    @ApiModelProperty(value = "目前未见异常人数")
    private Integer wjyc;
}
