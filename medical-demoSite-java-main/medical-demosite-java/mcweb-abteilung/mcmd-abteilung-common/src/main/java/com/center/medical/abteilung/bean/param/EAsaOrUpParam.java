package com.center.medical.abteilung.bean.param;

import com.center.medical.abteilung.bean.dto.EADataDto;
import com.center.medical.abteilung.bean.dto.EAFormDataDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 电测听保存参数
 */
@Data
public class EAsaOrUpParam implements Serializable {
    private static final long serialVersionUID = 8820328165472590667L;

    @ApiModelProperty(value = "保存更新数据")
    private EAFormDataDTO formdata;

    @ApiModelProperty(value = "保存更新数据")
    private EADataDto data;

    @ApiModelProperty(value = "体检号")
    private String patientCode;

    @ApiModelProperty(value = "科室id")
    private String ksId;

    @ApiModelProperty(value = "气导折线图路径")
    private String air_img;

    @ApiModelProperty(value = "骨导折线图路径")
    private String bone_img;
}
