package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 护理登记-弹窗数据
 */
@Data
public class NursingRegistration implements Serializable {
    private static final long serialVersionUID = 5173151742130785944L;

    @ApiModelProperty(value = "参数")
    private List<NursingRegistrationDto> param;
}
