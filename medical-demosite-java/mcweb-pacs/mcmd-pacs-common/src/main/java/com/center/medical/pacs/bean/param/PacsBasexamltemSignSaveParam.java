package com.center.medical.pacs.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author xhp
 * @since 2023-03-29 15:52
 */
@Data
public class PacsBasexamltemSignSaveParam implements Serializable {

    private static final long serialVersionUID = 6380540405688664784L;
    @ApiModelProperty(value = "pacs体征词id，新增时为空")
    private String id;

    @ApiModelProperty(value = "体征词名称", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(value = "结论词id")
    private String resultId;

    @ApiModelProperty(value = "体证词详细描述")
    private String bodyDetail;

    @ApiModelProperty(value = "重症级别", required = true)
    @NotNull
    private Integer intensiveLevel;

    @ApiModelProperty(value = "是否阳性结果：0或null.否 1.是", required = true)
    @NotNull
    private Integer isPositive;

    @ApiModelProperty(value = "是否默认选中：0.不选中 1.选中", required = true)
    @NotNull
    private Integer isDefault;

    @ApiModelProperty(value = "体征词显示顺序", required = true)
    @NotNull
    private Integer orderindex;

    @ApiModelProperty(value = "操作标识：{removed:移除，modified:修改，added：新增}", required = true)
    @NotBlank
    private String _state;
}
