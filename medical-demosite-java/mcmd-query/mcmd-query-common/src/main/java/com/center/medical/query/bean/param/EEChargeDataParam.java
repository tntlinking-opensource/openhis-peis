package com.center.medical.query.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 点击获取收费项目信息参数
 */
@Data
public class EEChargeDataParam implements Serializable {
    private static final long serialVersionUID = 1749949729021509007L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

}
