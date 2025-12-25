package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * 导入年度目标参数
 */
@Data
public class ImportTargetParam implements Serializable {
    private static final long serialVersionUID = 3802815615286853360L;


    @ApiModelProperty(value = "年份")
    private String year;


    @ApiModelProperty(value = "名单文件（excl）")
    private MultipartFile file;
}
