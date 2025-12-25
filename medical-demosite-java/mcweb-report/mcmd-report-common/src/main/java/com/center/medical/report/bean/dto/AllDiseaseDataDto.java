package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取所有可疑职业病模板上所需要的数据
 */
@Data
public class AllDiseaseDataDto implements Serializable {
    private static final long serialVersionUID = -1045611667120716112L;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "登记时间")
    private String dateregister;

    @ApiModelProperty(value = "本次体检异常")
    private String abnormal;

    @ApiModelProperty(value = "复查时间止")
    private String dateTo;

    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String medicaltype;
}
