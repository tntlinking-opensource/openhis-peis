package com.center.medical.center.qingdao.profession.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 危害因素检查列表
 */
@Data
public class ExamConclusionDto {

    @ApiModelProperty(value = "关联健康体检表")
    private String documentId;

    @ApiModelProperty(value = "体检机构名称")
    private String phyExamName;

    @ApiModelProperty(value = "体检机构代码")
    private String phyExamCode;

    @ApiModelProperty(value = "体检号")
    private String examNum;

    @ApiModelProperty(value = "职业病危害因素代码")
    private String itamCode;

    @ApiModelProperty(value = "危害因素名称")
    private String itamName;

    @ApiModelProperty(value = "体检结论代码")
    private String examConclusionCode;

    @ApiModelProperty(value = "疑似职业病代码")
    private String yszybCode;

    @ApiModelProperty(value = "职业禁忌证名称")
    private String zyjjzName;

    @ApiModelProperty(value = "其他疾病或异常描述")
    private String qtjbName;

    @ApiModelProperty(value = "其他疾病或异常描述")
    private String deptId;

    @ApiModelProperty(value = "接触相应职业病危害因素的用人单位名称")
    private String enterpriseName;
}
