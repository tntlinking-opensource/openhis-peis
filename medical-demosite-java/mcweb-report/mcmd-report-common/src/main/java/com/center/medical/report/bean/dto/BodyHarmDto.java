package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询所有危害因素对身体危害
 */
@Data
public class BodyHarmDto implements Serializable {
    private static final long serialVersionUID = 1075271145584311762L;

    @ApiModelProperty(value = "危害因素名称")
    private String harmName;

    @ApiModelProperty(value = "对健康影响")
    private String affect;

}
