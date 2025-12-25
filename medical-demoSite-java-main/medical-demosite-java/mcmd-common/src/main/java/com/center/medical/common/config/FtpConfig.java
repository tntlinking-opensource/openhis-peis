package com.center.medical.common.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分中心FTP配置信息
 *
 * @author 路飞
 */
@Data
public class FtpConfig implements Serializable {

    private static final long serialVersionUID = -7722345969273594316L;

    @ApiModelProperty(value = "FTP服务器地址")
    private String server;

    @ApiModelProperty(value = "FTP服务器端口号")
    private Integer port;

    @ApiModelProperty(value = "FTP登录用户名")
    private String user;

    @ApiModelProperty(value = "FTP登录密码")
    private String password;

    @ApiModelProperty(value = "上传允许的并发数")
    private Integer concurrency;

}
