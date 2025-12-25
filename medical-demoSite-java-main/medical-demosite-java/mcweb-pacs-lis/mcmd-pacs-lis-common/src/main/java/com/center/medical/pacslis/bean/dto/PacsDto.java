package com.center.medical.pacslis.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * pacs结果接口固定返回对象
 * @author xhp
 * @since 2023-03-07 10:36
 */
@ApiModel("pacs结果接口固定返回对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacsDto {
    @ApiModelProperty("收费项目接口代码")
    private String examfeeitemCode;
    @ApiModelProperty("检查时间")
    private Date examdatetime;
    @ApiModelProperty("检查师、检查人用户名")
    private String examdoctor;
    @ApiModelProperty("检查结果描述")
    private String examresultdesc;
    @ApiModelProperty("检查结果总结")
    private String examresultsummary;
    @ApiModelProperty("文件路径")
    private String imagefullpath;
    @ApiModelProperty("保存至pacsResult表userName字段")
    private String userName;
    @ApiModelProperty("pacs类型，com.center.medical.common.enums.PacsType.name()")
    private String type;
    @ApiModelProperty("peispatientfeeitem表的id")
    private String feeitemId;
    @ApiModelProperty(value = "审核者用户名")
    private String auditDoctor;

}
