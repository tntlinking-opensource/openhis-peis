package com.center.medical.abteilung.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class SectionResultMainParam implements Serializable {
    private static final long serialVersionUID = 6321021597291430278L;

    @ApiModelProperty(value = "是否补全,true是，false否")
    private String autoFill;

    @ApiModelProperty(value = "体检号")
    private String patientCode;

}
