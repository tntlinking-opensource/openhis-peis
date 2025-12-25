package com.center.medical.system.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 修改科室id参数
 */
@Data
public class ModifyDepartmentParam implements Serializable {
    private static final long serialVersionUID = 1849635445965673250L;


    @NotBlank(message = "收费项目id不能为空")
    @ApiModelProperty(value = "收费项目id")
    private String id;

    @NotBlank(message = "科室id不能为空")
    @ApiModelProperty(value = "科室id")
    private String ksId;
}
