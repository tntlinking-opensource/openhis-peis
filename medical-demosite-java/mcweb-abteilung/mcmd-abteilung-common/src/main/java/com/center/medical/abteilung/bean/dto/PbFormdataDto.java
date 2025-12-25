package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 快捷开药-保存更新数据
 */
@Data
public class PbFormdataDto implements Serializable {
    private static final long serialVersionUID = 3919252217368053865L;

    @ApiModelProperty(value = "开药原因")
    private String reason;


    @ApiModelProperty(value = "体检号")
    private String patientcode;

}
