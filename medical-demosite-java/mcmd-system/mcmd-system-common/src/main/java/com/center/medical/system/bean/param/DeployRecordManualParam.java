package com.center.medical.system.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 人工处理
 * @author xhp
 * @since 2023-12-02 10:27
 */
@Data
@ApiModel(value = "DeployVersionSaveParam", description = "人工处理")
public class DeployRecordManualParam implements Serializable {
    @ApiModelProperty(value = "科室id",required = true)
    @NotEmpty
    private List<String> ksIpIds;

    @ApiModelProperty(value = "版本信息id",required = true)
    @NotNull
    private Integer versionId;
}
