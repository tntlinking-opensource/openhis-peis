package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SaveTreatParam implements Serializable {
    private static final long serialVersionUID = 2718482303572423893L;

    @ApiModelProperty(value = "体检号")
    private String patientno;


    @ApiModelProperty(value = "ids")
    private List<String> data;
}
