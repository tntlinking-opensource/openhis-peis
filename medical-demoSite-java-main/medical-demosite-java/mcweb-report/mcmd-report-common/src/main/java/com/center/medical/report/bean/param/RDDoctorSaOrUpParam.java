package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 添加默认医生签名参数
 */
@Data
public class RDDoctorSaOrUpParam implements Serializable {
    private static final long serialVersionUID = -3079772138852724676L;

    @ApiModelProperty(value = "ID")
    private String id;

    @NotBlank(message = "科室ID不能为空!")
    @ApiModelProperty(value = "科室ID")
    private String depId;

    @NotBlank(message = "用户编号不能为空!")
    @ApiModelProperty(value = "用户编号")
    private String userId;

    @NotNull(message = "类型不能为空!")
    @ApiModelProperty(value = "类型：0:健康 1:职业 2:健康+职业")
    private Integer type;

    @NotNull(message = "人员类型不能为空!")
    @ApiModelProperty(value = "人员类型：0:审核者 1:医师签名 2:审核者+医师签名")
    private Integer personnelType;
}
