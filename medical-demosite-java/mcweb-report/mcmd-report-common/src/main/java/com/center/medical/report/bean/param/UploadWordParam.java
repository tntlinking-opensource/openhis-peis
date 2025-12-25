package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * 上传团检报告word参数
 */
@Data
public class UploadWordParam implements Serializable {
    private static final long serialVersionUID = -1100277052171337053L;

    @ApiModelProperty(value = "文件")
    private MultipartFile file;


    @ApiModelProperty(value = "id")
    private String id;

}
