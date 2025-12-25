package com.center.medical.abteilung.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 肺功能 读卡参数
 */
@Data
public class DivisionLungParam implements Serializable {
    private static final long serialVersionUID = 7721346624274993380L;

    @ApiModelProperty(value = "是否补全，true是false否")
    private String autoFill;

    @ApiModelProperty(value = "体检号")
    private String patientCode;

    @ApiModelProperty(value = "科室ID")
    private String ksId;

}
