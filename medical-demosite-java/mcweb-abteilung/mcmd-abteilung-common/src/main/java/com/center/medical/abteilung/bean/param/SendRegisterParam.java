package com.center.medical.abteilung.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 外送登记参数
 */
@Data
public class SendRegisterParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 8987693962020502994L;

    @ApiModelProperty(value = "外送机构")
    private String wsjg;


    @ApiModelProperty(value = "姓名")
    private String patientname;


    @ApiModelProperty(value = "体检号")
    private String patientcode;

}
