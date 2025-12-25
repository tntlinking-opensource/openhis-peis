package com.center.medical.pacslis.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xhp
 * @since 2023-02-21 10:04
 */
@Data
public class LisExamDto {
    @ApiModelProperty(value = "检查项目id")
    private String id;
    @ApiModelProperty(value = "检查项目接口代码")
    private String interfaceCode;
    @ApiModelProperty(value = "男性refHigh")
    private Double valuemalemax;
    @ApiModelProperty(value = "男性refLow")
    private Double valuemalemin;
    @ApiModelProperty(value = "女性refHigh")
    private Double valuefemalemax;
    @ApiModelProperty(value = "女性refLow")
    private Double valuefemalemin;
    @ApiModelProperty(value = "收费项目中检查项目行序")
    private Integer orderIndex;
    @ApiModelProperty(value = "检查项目名称")
    private String examitemName;
    @ApiModelProperty(value = "描述进小结")
    private Integer isDesc;
    @ApiModelProperty(value = "手动录入")
    private Integer byHand;
    @ApiModelProperty(value = "不必等待")
    private Integer nowait;
    @ApiModelProperty(value = "检查项目打印名称")
    private String examitemNameprn;
    @ApiModelProperty(value = "判断字符结果中的大于号小于号  1 < 2 > 3<>")
    private String examitemCodehm;
    @ApiModelProperty(value = "只要结果不是未见或阴性就要算做异常")
    private Boolean  itemFlag;
    @ApiModelProperty(value = "结论词(高)")
    private String idConclusionhi;
    @ApiModelProperty(value = "结论词(低)")
    private String idConclusionlo;
    @ApiModelProperty(value = "结论词(阳)")
    private String idConclusionpo;
    @ApiModelProperty(value = "结论词(阴)")
    private String idConclusionne;
    @ApiModelProperty(value = "危急值上限")
    private Double valuedangerousmax;
    @ApiModelProperty(value = "危急值下限")
    private Double valuedangerousmin;
    @ApiModelProperty(value = "检查项目艾迪康代码")
    private String examitemCode3;

}
