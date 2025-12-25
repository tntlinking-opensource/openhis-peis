package com.center.medical.pacslis.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 彩超审核查看参数
 */
@Data
public class DivisionParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = -937417998134790591L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "科室id")
    private String ksId;


}
