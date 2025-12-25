package com.center.medical.common.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 自动部署-保存更新记录
 * @author xhp
 * @since 2023-11-15 14:31
 */
@Data
public class AutoDeploySaveRecordParam {
    @ApiModelProperty(value = "是否更新成功 0失败 1成功",required = true)
    @NotNull
    private Integer isSuccess;

    @ApiModelProperty(value = "sys_deploy_version.id",required = true)
    @NotNull
    private Integer versionId;

    @ApiModelProperty(value = "md_ks_ip.id",required = true)
    @NotBlank
    private String ksIpId;

    @ApiModelProperty(value = "错误信息")
    private String message;

    @ApiModelProperty(value = "ip")
    private String address;

    @ApiModelProperty(value = "主机名")
    private String hostName;
}
