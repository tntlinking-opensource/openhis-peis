package com.center.medical.abteilung.bean.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class Tjregs implements Serializable {

    private static final long serialVersionUID = -3115168347245653445L;

    @ApiModelProperty(value = "危急值(用于判断是否插入高危人员)")
    private String isDanger;

    @ApiModelProperty(value = "弃检")
    private String isUnchecked;

    @ApiModelProperty(value = "体检者")
    private String patientcode;
}
