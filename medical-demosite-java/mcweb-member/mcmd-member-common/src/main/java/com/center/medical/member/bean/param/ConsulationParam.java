package com.center.medical.member.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 咨询与随访分页参数
 */
@Data
public class ConsulationParam extends BaseParam implements Serializable {

    @ApiModelProperty(value = "咨询人姓名")
    private String consultName;

    @ApiModelProperty(value = "咨询人电话")
    private String consultPhone;

    @ApiModelProperty(value = "咨询医生")
    private String doctorUsername;

    @ApiModelProperty(value = "类别 1.现场咨询 2.来电咨询 3.电话回访")
    private String consultType;


}
