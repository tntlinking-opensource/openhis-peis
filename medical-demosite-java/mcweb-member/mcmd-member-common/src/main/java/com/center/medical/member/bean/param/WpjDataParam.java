package com.center.medical.member.bean.param;

import com.center.medical.common.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class WpjDataParam implements Serializable {


    private static final long serialVersionUID = 776053558933450523L;


    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检号")
    private String patientcode;


    public String getPatientcode() {
        if (StringUtils.isEmpty(patientcode)) {
            return null;
        }
        return patientcode.toUpperCase();
    }


    @ApiModelProperty(value = "手机号")
    private String phone;


    @ApiModelProperty(value = "团体名称")
    private String orgName;
}
