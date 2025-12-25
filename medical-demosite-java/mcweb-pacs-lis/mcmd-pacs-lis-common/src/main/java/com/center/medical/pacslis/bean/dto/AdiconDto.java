package com.center.medical.pacslis.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 艾迪康结果接口固定返回对象
 */
@ApiModel("艾迪康结果接口固定返回对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdiconDto implements Serializable {
    private static final long serialVersionUID = -2468665547094411476L;
    @ApiModelProperty("检查时间")
    private LocalDateTime examDateTime;
    @ApiModelProperty("收费项目代码")
    private String itemCode;
    @ApiModelProperty("检查项目代码")
    private String examCode;
    @ApiModelProperty("LIS样本标号")
    private Integer lisybbh;
    @ApiModelProperty("数字结果")
    private Double examItemValuesNumber;
    @ApiModelProperty("字符结果")
    private String examItemValuesShort;
    @ApiModelProperty("参考范围（lis范围）")
    private String refRange;
    @ApiModelProperty("状态")
    private String status;
    @ApiModelProperty("单位")
    private String units;
    @ApiModelProperty("Lis代码")
    private String lisCode;
    @ApiModelProperty("报告结果")
    private String examItemValuesReport;
    @ApiModelProperty("检查医师")
    private String examDoctor;
    @ApiModelProperty("审核人")
    private String auditName;
    @ApiModelProperty("检验师")
    private String inspectName;
    @ApiModelProperty("审核时间")
    private LocalDateTime auditDate;
    @ApiModelProperty("检查医生代码")
    private String inspectCode;
    @ApiModelProperty("收样时间（虹桥lis）")
    private LocalDateTime receiveDate;
    @ApiModelProperty("艾迪康代码")
    private String adiconCode;
}
