package com.center.medical.pacslis.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 取得已预约客户参数
 */
@Data
public class ReservationUserParam implements Serializable {

    private static final long serialVersionUID = 8583284111398679924L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "身份证号")
    @ApiParam(hidden = true)
    private String userNo;

    @ApiModelProperty(value = "type")
    @ApiParam(hidden = true)
    private int type;


}
