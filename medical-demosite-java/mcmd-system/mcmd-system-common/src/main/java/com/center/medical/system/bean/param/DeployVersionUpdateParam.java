package com.center.medical.system.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 更新版本信息
 *
 * @author xhp
 * @since 2023-12-02 9:28
 */
@Data
@ApiModel(value = "DeployVersionUpdateParam", description = "更新版本信息")
public class DeployVersionUpdateParam implements Serializable {

    private static final long serialVersionUID = -5357470830970104845L;

    @ApiModelProperty(value = "版本信息id", required = true)
    @NotNull
    private Integer id;

    @ApiModelProperty(value = "版本号", required = true)
    @NotBlank
    private String versionNumber;

    @ApiModelProperty(value = "版本名称")
    private String versionName;

    @ApiModelProperty(value = "要求更新时间", required = true)
    @NotNull
    private Date executeTime;

    @ApiModelProperty(value = "备注")
    private String notes;

    @ApiModelProperty(value = "更新类型 详见com.center.medical.bean.enums.DeployType", required = true)
    @NotNull
    private Integer updateType;

    @ApiModelProperty(value = "更新包路径")
    private String filePath;

    @ApiModelProperty(value = "数据库更新语句")
    private String updateSql;

    @ApiModelProperty(value = "是否启用 0否 1是", required = true)
    @NotNull
    private Boolean isEnable;

    @ApiModelProperty(value = "分中心id多个")
    private List<String> branchIds;
}
