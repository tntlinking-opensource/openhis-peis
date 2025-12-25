package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 家庭卡生日分页参数
 */
@Data
public class FamilyBirthParam implements Serializable {
    private static final long serialVersionUID = -2041312647815645981L;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "卡类型")
    private String card;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;
}
