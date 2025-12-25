package com.center.medical.abteilung.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 检验样本-样本录入参数
 */
@Data
public class SampleDeliveryParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = 5032421091237872009L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

}
