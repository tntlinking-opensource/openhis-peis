package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 本次职业健康检查危害因素人员分布情况一览表统计
 */
@Data
public class AllPeopleNumDto implements Serializable {
    private static final long serialVersionUID = 6378265760164013590L;


    @ApiModelProperty(value = "危害因素名称")
    private String harmName;

    @ApiModelProperty(value = "应查人数")
    private Integer peopleNum;

    @ApiModelProperty(value = "实查人数")
    private Integer inspectNum;

    @ApiModelProperty(value = "未查人数")
    private Integer unexploredNum;

    @ApiModelProperty(value = "男性人数")
    private Integer manNum;

    @ApiModelProperty(value = "女性人数")
    private Integer womenNum;

}
