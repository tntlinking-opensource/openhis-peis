package com.center.medical.abteilung.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 职业性问诊体检者列表数据参数
 */
@Data
public class PatientDataParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -4431655843694345503L;

    @ApiModelProperty(value = "科室id")
    private String ksID;




}
