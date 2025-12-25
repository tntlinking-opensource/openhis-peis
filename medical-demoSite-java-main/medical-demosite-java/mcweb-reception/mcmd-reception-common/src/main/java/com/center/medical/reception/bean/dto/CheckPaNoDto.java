package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 校正档案号 数据
 */
@Data
public class CheckPaNoDto implements Serializable {
    private static final long serialVersionUID = -5669144791321493368L;


    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "档案号")
    private String patientarchiveno;


}
