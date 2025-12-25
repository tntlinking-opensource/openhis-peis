package com.center.medical.abteilung.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 电测听上传图片
 */
@Data
public class EAUploadImgParam implements Serializable {
    private static final long serialVersionUID = 3799243853038538033L;

    @ApiModelProperty(value = "体检号")
    private String patientCode;

    @ApiModelProperty(value = "气导折线图路径")
    private String airImgPath;

    @ApiModelProperty(value = "骨导折线图路径")
    private String boneImgPath;

}
