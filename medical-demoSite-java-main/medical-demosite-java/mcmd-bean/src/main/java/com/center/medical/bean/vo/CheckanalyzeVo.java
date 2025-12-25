package com.center.medical.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 检出统计返回数据
 */
@Data
public class CheckanalyzeVo implements Serializable {
    private static final long serialVersionUID = 6675975948148558337L;

    @ApiModelProperty(value = "部门名称")
    private String depName;

    @ApiModelProperty(value = "结论词")
    private String conclusion;

    @ApiModelProperty(value = "检出人数-男")
    private Integer detectionMale;

    @ApiModelProperty(value = "检出人数-女")
    private Integer detectionFemale;

    @ApiModelProperty(value = "检出人数-合计")
    private Integer detectionTotal;

    @ApiModelProperty(value = "总体人数-男")
    private Integer allMale;

    @ApiModelProperty(value = "总体人数-女")
    private Integer allFemale;

    @ApiModelProperty(value = "总体人数-合计")
    private Integer allTotal;

    @ApiModelProperty(value = "总体百分比-男")
    private Double checkMale;

    @ApiModelProperty(value = "总体百分比-女")
    private Double checkFemale;

    @ApiModelProperty(value = "总体百分比-合计")
    private Double checkTotal;

}
