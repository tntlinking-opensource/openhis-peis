package com.center.medical.data.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 症状数据参数
 */
@Data
public class ShowDataParam implements Serializable {
    private static final long serialVersionUID = -6082939604493653343L;

    @ApiModelProperty(value = "体检号")
    private String patientCode;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "接害因素")
    private String[] jhys;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String medicaltype;
}
