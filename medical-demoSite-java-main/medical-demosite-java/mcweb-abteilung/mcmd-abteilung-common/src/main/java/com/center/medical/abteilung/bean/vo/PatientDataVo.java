package com.center.medical.abteilung.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PatientDataVo implements Serializable {
    private static final long serialVersionUID = -6224738661977835544L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "性别")
    private String idSex;

    @ApiModelProperty(value = "是否审核：0.未审核 1.已审核")
    private String isAudit;

    @ApiModelProperty(value = "姓名")
    private String patientname;
}
