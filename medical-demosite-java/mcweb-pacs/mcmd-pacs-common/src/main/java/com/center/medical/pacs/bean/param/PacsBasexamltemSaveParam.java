package com.center.medical.pacs.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author xhp
 * @since 2023-03-29 15:48
 */
@Data
public class PacsBasexamltemSaveParam implements Serializable {
    private static final long serialVersionUID = -4205803081598344565L;
    
    @ApiModelProperty(value = "pacs检查项目id,新增时为空")
    private String id;

    @ApiModelProperty(value = "检查项目名称", required = true)
    @NotBlank
    private String examitemName;

    @ApiModelProperty(value = "性别：0.代表男 1.代表女 2.通用", required = true)
    @NotNull
    private Integer fMale;

    @ApiModelProperty(value = "输入码", required = true)
    @NotBlank
    private String inputCode;

    @ApiModelProperty(value = "科室ID", required = true)
    @NotBlank
    private String divisionId;

    @ApiModelProperty(value = "类型：0.健康检查类型 1.职业检查类型 2.健康+职业", required = true)
    @NotNull
    private Integer type;

    @ApiModelProperty(value = "体征词")
    @Valid
    private List<PacsBasexamltemSignSaveParam> signs;

}
