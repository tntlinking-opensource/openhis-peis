package com.center.medical.center.deploy.bean.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 自动部署-更新版本信息(DeployVersion表实体类
 *
 * @author makejava
 * @since 2023-11-15 08:42:31
 */
@Data
//value = "DeployVersion", description = "自动部署-更新版本信息"
public class DeployVersionDto implements Serializable {
    private static final long serialVersionUID = 6176155281929878841L;

    // "id"
    private Integer id;

    // "版本号"
    private String versionNumber;

    // "版本名称"
    private String versionName;

    // "要求更新时间"
    private Date executeTime;

    // "备注"
    private String notes;

    // "更新包路径"
    private String filePath;

    // "启动更新服务命令"
    private String deployStartupCommand;

    // "服务启动命令"
    private String serviceStartupCommand;

    // "更新服务地址"
    private String deployServiceAddr;

    // "下载到本地后的jar包路径，带文件名")
    private String downloadJarPath;

    // "主服务地址")
    private String mainUrl;

    // "服务实际运行的jar包路径，带文件名")
    private String jarPath;

    // "被更新服务地址")
    private String serviceAddr;

    // "md_ks_ip表的id")
    private String ksIpId;
}
