package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 单位预约提交参数
 */
@Data
public class UnitReservationParam implements Serializable {
    private static final long serialVersionUID = -6757529044412649603L;

    @ApiModelProperty(value = "公司id")
    private String idOrg;

    @ApiModelProperty(value = "预约码")
    private String extractedCode;

    @ApiModelProperty(value = "预约人")
    private String patientname;

    @ApiModelProperty(value = "分组id")
    private String groupId;

    @ApiModelProperty(value = "手机号")
    private String phone;

}
