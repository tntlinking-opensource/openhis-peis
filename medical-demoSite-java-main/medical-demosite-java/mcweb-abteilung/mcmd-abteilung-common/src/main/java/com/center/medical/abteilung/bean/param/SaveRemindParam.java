package com.center.medical.abteilung.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 保存科室提醒参数
 */
@Data
public class SaveRemindParam implements Serializable {
    private static final long serialVersionUID = 4688166430270604805L;

    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @ApiModelProperty(value = "科室id")
    private String depId;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "被提醒科室IDs,逗号分割")
    private String depIds;

    @ApiModelProperty(value = "内容")
    private String content;

}
