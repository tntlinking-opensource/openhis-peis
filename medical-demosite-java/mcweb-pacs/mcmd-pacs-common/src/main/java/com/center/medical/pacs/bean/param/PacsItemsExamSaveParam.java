package com.center.medical.pacs.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author xhp
 * @since 2023-04-03 9:37
 */
@Data
public class PacsItemsExamSaveParam implements Serializable {

    private static final long serialVersionUID = -2225591475458798335L;
    @ApiModelProperty(value = "收费项目和检查项目关联表id,新增时为空")
    private String id;

    @ApiModelProperty(value = "检查项目ID", required = true)
    @NotBlank
    private String inspectId;

    @ApiModelProperty(value = "排序", required = true)
    @NotNull
    private Integer orderIndex;

    @ApiModelProperty(value = "操作标识：{removed:移除，modified:修改，added：新增}", required = true)
    @NotBlank
    private String _state;
}
