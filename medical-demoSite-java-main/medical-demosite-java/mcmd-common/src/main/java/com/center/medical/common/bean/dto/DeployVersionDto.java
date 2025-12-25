package com.center.medical.common.bean.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 自动部署-更新版本信息(DeployVersion)表实体类
 *
 * @author makejava
 * @since 2023-11-15 08:42:31
 */
@Data
@ApiModel(value = "DeployVersion", description = "自动部署-更新版本信息")
public class DeployVersionDto implements Serializable {
    private static final long serialVersionUID = 6176155281929878841L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "版本号")
    private String versionNumber;

    @ApiModelProperty(value = "版本名称")
    private String versionName;

    @ApiModelProperty(value = "要求更新时间")
    private Date executeTime;

    @ApiModelProperty(value = "备注")
    private String notes;

    @ApiModelProperty(value = "更新包路径，用于下载jar包")
    private String filePath;

    @ApiModelProperty(value = "更新服务启动命令")
    private String deployStartupCommand;

    @ApiModelProperty(value = "服务启动命令")
    private String serviceStartupCommand;

    @ApiModelProperty(value = "更新服务地址")
    private String deployServiceAddr;

    @ApiModelProperty(value = "下载到本地后的jar包路径，带文件名。如果是主服务，就是新版本jar包的路径。")
    private String downloadJarPath;

    @ApiModelProperty(value = "主服务地址")
    private String mainUrl;

    @ApiModelProperty(value = "服务实际运行的jar包路径，带文件名")
    private String jarPath;

    @ApiModelProperty(value = "被更新服务地址")
    private String serviceAddr;

    @ApiModelProperty(value = "md_ks_ip表的id")
    private String ksIpId;
}
