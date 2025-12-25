package com.center.medical.pacs.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author xhp
 * @since 2023-04-03 9:37
 */
@Data
public class PacsItemsSaveParam implements Serializable {
    private static final long serialVersionUID = 1809304545712700520L;

    @ApiModelProperty(value = "id,新增时为空")
    private String id;

    @ApiModelProperty(value = "收费项目名称", required = true)
    @NotBlank
    private String examfeeitemName;

    @ApiModelProperty(value = "收费项目输入码", required = true)
    @NotBlank
    private String sfxmsrm;

    @ApiModelProperty(value = "是否启用 0否 1是", required = true)
    @NotBlank
    private String sysmanmark;

    @ApiModelProperty(value = "收费项目打印名称", required = true)
    @NotBlank
    private String examfeeitemNameprn;

    @ApiModelProperty(value = "打印排列序号")
    private String xh;

    @ApiModelProperty(value = "性别 0男1女2通用", required = true)
    @NotBlank
    private String xb;

    @ApiModelProperty(value = "收费项目接口代码")
    private String examfeeitemCode;

    @ApiModelProperty(value = "部位IDs")
    private String examfeeitemCodehm;

    @ApiModelProperty(value = "所属科室ID", required = true)
    @NotBlank
    private String idDepart;

    @ApiModelProperty(value = "所属科室名称", required = true)
    @NotBlank
    private String departName;

    @ApiModelProperty(value = "检查意义")
    private String jcyy;

    @ApiModelProperty(value = "检查项目")
    @Valid
    private List<PacsItemsExamSaveParam> exams;
}
