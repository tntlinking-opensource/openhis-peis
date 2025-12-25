package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  肺功能 审核 结论词数据
 */
@Data
public class DLGriddataDto implements Serializable {
    private static final long serialVersionUID = -8944435012147379440L;

    @ApiModelProperty(value = "体征词Id")
    private String tzcId;

    @ApiModelProperty(value = "结论词Id")
    private String jlcId;

    @ApiModelProperty(value = "在搜索结论词与小结时必须先以本ID查询到所有的相关结论词，然后再按照检查项目体证词关联表ID找到所对应的体证词（小结）与结论词ID")
    private String verdictId;

    @ApiModelProperty(value = "结论词名称")
    private String jlcName;

    @ApiModelProperty(value = "收费项目ID")
    private String chargesId;
}
