package com.center.medical.reception.bean.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 体检卡退费
 */
@Data
public class ReceiveTjkCardParam implements Serializable {
    private static final long serialVersionUID = 8716185283316014200L;

    @ApiModelProperty(value = "版本号")
    private Long version;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "卡数据")
    private TJKFormDataParam formdata;

    @ApiModelProperty(value = "消费数据")
    private TJKDataParam data;



}
