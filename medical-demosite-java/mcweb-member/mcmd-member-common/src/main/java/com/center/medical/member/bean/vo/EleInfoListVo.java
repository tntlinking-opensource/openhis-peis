package com.center.medical.member.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 档案下体检者明细数据
 */
@Data
public class EleInfoListVo implements Serializable {

    private static final long serialVersionUID = -4677928803695214799L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "地址")
    private String address;
}
