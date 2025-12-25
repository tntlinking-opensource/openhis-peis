package com.center.medical.abteilung.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 职业性问诊-职业病史列表数据
 */
@Data
public class RemindPatientParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 8579076019539360192L;

    @ApiModelProperty(value = "科室id(必填)")
    private String ksID;

    @ApiModelProperty(value = "体检码(必填)")
    private String patientcode;
}
