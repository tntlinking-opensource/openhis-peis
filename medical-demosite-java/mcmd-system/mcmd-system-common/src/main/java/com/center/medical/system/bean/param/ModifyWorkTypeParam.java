package com.center.medical.system.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 修改工种参数
 */
@Data
public class ModifyWorkTypeParam implements Serializable {
    private static final long serialVersionUID = 1867116593331051569L;

    @NotNull(message = "体检号不能为空")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @NotNull(message = "工种id不能为空")
    @ApiModelProperty(value = "工种id")
    private String worktypeId;

    @NotNull(message = "工种名称不能为空")
    @ApiModelProperty(value = "工种名称")
    private String tradesName;
}
