package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取所有职业禁忌证模板上所需要的数据
 */
@Data
public class AllContraindicatedDto implements Serializable {
    private static final long serialVersionUID = 570199084266863672L;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "登记时间")
    private String dateregister;

    @ApiModelProperty(value = "职业禁忌症名称")
    private String diagnosis;

    @ApiModelProperty(value = "危害因素名字")
    private String harmName;

    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String medicaltype;

}
