package com.center.medical.center.qingdao.profession.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 检查项目明细结果
 */
@Data
public class ExamItemResultDto {

    @ApiModelProperty(value = "关联健康体检表")
    private String documentId;

    @ApiModelProperty(value = "体检机构名称")
    private String phyExamName;

    @ApiModelProperty(value = "体检机构代码")
    private String phyExamCode;

    @ApiModelProperty(value = "体检号")
    private String examNum;

    @ApiModelProperty(value = "检查项目表中的项目名称")
    private String examItemPname;

    @ApiModelProperty(value = "检查项目表中的项目代码")
    private String examItemPcode;

    @ApiModelProperty(value = "省职业健康系统检查子项目名称")
    private String examItemName;

    @ApiModelProperty(value = "省职业健康系统检查子项目代码")
    private String examItemCode;

    @ApiModelProperty(value = "检查结果类型代码,01 数值型、02 字符型、03枚举型")
    private String examResultType;

    @ApiModelProperty(value = "检查结果类型代码,01 数值型、02 字符型、03枚举型")
    private String examResult;

    @ApiModelProperty(value = "检查结果类型代码,01 数值型、02 字符型、03枚举型")
    private String examItemUnitCode;

    @ApiModelProperty(value = "参考值范围小值")
    private String referenceRangeMin;

    @ApiModelProperty(value = "参考值范围大值")
    private String referenceRangeMax;

    @ApiModelProperty(value = "是否异常")
    private String abnormal;

    @ApiModelProperty(value = "平台部门编号")
    private String deptId;

}
