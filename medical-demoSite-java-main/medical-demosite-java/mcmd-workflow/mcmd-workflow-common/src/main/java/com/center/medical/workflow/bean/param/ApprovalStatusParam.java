package com.center.medical.workflow.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 我的审批情况
 */
@Data
public class ApprovalStatusParam implements Serializable {
    private static final long serialVersionUID = -7051845833835606862L;

    @ApiModelProperty(value = "实例名称")
    private String caseName;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "用户编号")
    private String userNo;
}
