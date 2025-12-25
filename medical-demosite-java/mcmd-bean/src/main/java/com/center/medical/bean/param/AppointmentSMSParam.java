package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约短信发送 参数
 */
@Data
public class AppointmentSMSParam implements Serializable {
    private static final long serialVersionUID = -4212248720842824316L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检者类型ID")
    private String idPatientclass;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "预约时间")
    private Date dateguidancereturned;

    @ApiModelProperty(value = "电话")
    private String phone;

    public AppointmentSMSParam(String patientcode, String idPatientclass, String patientname, Date dateguidancereturned, String phone) {
        this.patientcode = patientcode;
        this.idPatientclass = idPatientclass;
        this.patientname = patientname;
        this.dateguidancereturned = dateguidancereturned;
        this.phone = phone;
    }

    public AppointmentSMSParam() {
    }
}
