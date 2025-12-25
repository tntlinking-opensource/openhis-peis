package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-04-04 9:36
 * @description: 检查是否可预约参数
 */
@Data
public class CheckAppointmentParam implements Serializable {
    private static final long serialVersionUID = 4306914140127669898L;

    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "身份证号", required = true, position = 6)
    private String idcard;

    @ApiModelProperty(value = "预约类型ID", required = true, position = 2)
    private Integer levelId;

    @ApiModelProperty(value = "订单类型：0.个检 1.团检")
    private Integer fUsecodehiden;

    public CheckAppointmentParam(String numorgresv, String patientcode, String idcard, Integer levelId, Integer fUsecodehiden) {
        this.numorgresv = numorgresv;
        this.patientcode = patientcode;
        this.idcard = idcard;
        this.levelId = levelId;
        this.fUsecodehiden = fUsecodehiden;
    }
}
