package com.center.medical.system.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: 路飞
 * @date: 2023/7/4 15:57
 * @description: 系统版本更新部署执行参数
 */
@Data
public class DeloyDto {

    @ApiModelProperty(value = "数据库更新文件")
    List<String> sqlFiles;

    @ApiModelProperty(value = "安装包文件名")
    private String fileName;

    @ApiModelProperty(value = "启动配置文件名")
    private String ymlActive;

    @ApiModelProperty(value = "安装包文件路径")
    private String serviceFile;

    @ApiModelProperty(value = "脚本路径")
    private String scriptPath;

    @ApiModelProperty(value = "操作：1.启动 2.停止 3.重启 4.查看状态 ")
    private String command;

    @ApiModelProperty(value = "系统服务Id(版本控制-分中心版本更新记录的ID)")
    private String id;

}
