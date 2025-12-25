package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SaveTreatmentParam implements Serializable {
    private static final long serialVersionUID = 3252965862737124296L;

    @ApiModelProperty(value = "体检号")
    private String patientno;

    @ApiModelProperty(value = "id的集合")
    private List<String> ids;




}
