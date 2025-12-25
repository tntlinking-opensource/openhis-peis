package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 健康+职业 结论词下拉
 */
@Data
public class BasconclusionVo implements Serializable {

    private static final long serialVersionUID = 3460544496821343709L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "结论名称")
    private String name;

    @ApiModelProperty(value = "总检建议")
    private String suggestion;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "拼音输入码")
    private String inputCode;

    @ApiModelProperty(value = "团体建议")
    private String suggestiongroup;

    @ApiModelProperty(value = "维护（创建）人")
    private String creater;
}
