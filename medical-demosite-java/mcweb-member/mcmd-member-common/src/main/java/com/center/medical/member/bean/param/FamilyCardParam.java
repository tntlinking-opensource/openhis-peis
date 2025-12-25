package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 家庭会员-家庭卡消费参数
 */
@Data
public class FamilyCardParam implements Serializable {

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "卡种类")
    private String card;

    @ApiModelProperty(value = "卡号")
    private String cardno;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;
}
