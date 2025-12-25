package com.center.medical.abteilung.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 修改检查项目已检或未检
 */
@Data
public class ModifyStatusParam implements Serializable {
    private static final long serialVersionUID = -447124016457292863L;

    @NotEmpty(message = "ids不能为空！")
    @ApiModelProperty(value = "多个id用逗号分割")
    private List<String> ids;

    @NotNull(message = "fExaminated不能为空！")
    @ApiModelProperty(value = "0未检1已检")
    private Integer fExaminated;


}
