package com.center.medical.report.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取提醒接口参数
 */
@Data
public class RemindPatientParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -2857013199191738297L;

    @ApiModelProperty(value = "科室id")
    private String ksID;

    @ApiModelProperty(value = "体检码")
    private String patientcode;
}
