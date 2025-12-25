package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 会员卡退款参数
 */
@Data
public class ReceiveMemberParam implements Serializable {
    private static final long serialVersionUID = -2691333814955803437L;

    @ApiModelProperty(value = "类型,传saveCard")
    private String type;

    @ApiModelProperty(value = "版本")
    private Long version;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "formdata")
    private RMFormDataParam formdata;

    @ApiModelProperty(value = "消费数据")
    private TJKDataParam data;

}
