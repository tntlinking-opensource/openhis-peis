package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 右侧人员信息 参数
 */
@Data
public class LoadSampleParam implements Serializable {
    private static final long serialVersionUID = -5470591361453719943L;

    @ApiModelProperty(value = "组ID")
    private String groupId;

    @ApiModelProperty(value = "订单ID")
    private String orderId;

    @ApiModelProperty(value = "报告ID")
    private String reportId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

}
