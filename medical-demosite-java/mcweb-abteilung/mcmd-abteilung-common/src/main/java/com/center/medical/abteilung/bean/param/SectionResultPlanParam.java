package com.center.medical.abteilung.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class SectionResultPlanParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = 4393479203331569249L;

    @ApiModelProperty(value = "体检科室")
    private String depId;

    @ApiModelProperty(value = "审核状态")
    private String status;

    @ApiModelProperty(value = "医师")
    private String doctor;

    @ApiModelProperty(value = "团检单位")
    private String orgId;

}
