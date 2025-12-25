package com.center.medical.center.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 图片上传配置类
 * @author xhp
 * @since 2023-06-25 15:06
 */
@Data
@Component
@ConfigurationProperties(prefix = "img")
public class UploadImageConfig {
    //本机科室编号
    private String deptNo;
    //本机图片上传目录
    private String localpath;
    //上传到服务器，服务器上存放图片的文件夹名
    public static final String IMAGE_PATH = "image";
}
