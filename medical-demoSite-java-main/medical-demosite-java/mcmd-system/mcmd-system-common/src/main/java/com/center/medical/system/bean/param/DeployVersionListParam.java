package com.center.medical.system.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询版本信息
 * @author xhp
 * @since 2023-12-02 10:03
 */
@Data
@ApiModel(value = "DeployVersionSaveParam", description = "查询版本信息")
public class DeployVersionListParam  implements Serializable {

    private static final long serialVersionUID = -5116111913512276017L;

    @ApiModelProperty(value = "版本号")
    private String versionNumber;

    @ApiModelProperty(value = "版本名称")
    private String versionName;

    @ApiModelProperty(value = "更新类型 详见com.center.medical.bean.enums.DeployType")
    private Integer updateType;
}
