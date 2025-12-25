package com.center.medical.report.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 选择危害因素
 */
@Data
public class ZyChooseVo implements Serializable {
    private static final long serialVersionUID = -1118408299974606913L;

    @ApiModelProperty(value = "接害因素id")
    private String harmId;

    @ApiModelProperty(value = "接害因素名称")
    private String harmName;

}
