package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 护理登记-弹窗数据
 */
@Data
public class NursingRegistrationDto implements Serializable {
    private static final long serialVersionUID = 2176876861898683079L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "0健康1职业2综合")
    private Integer dh;
}
