package com.center.medical.center.qingdao.profession.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 潍坊疾控上传数据
 */
@Data
public class WfjkContentDto {

    @ApiModelProperty(value = "体检报告列表")
    private List<HealthPhysicalReportDto> healthPhysicalReports;


    @ApiModelProperty(value = "危害因素检查列表")
    private List<ExamConclusionDto> examConclusionLists;


    @ApiModelProperty(value = "检查项目明细结果列表")
    private List<ExamItemResultDto> examItemResultLists;

}
