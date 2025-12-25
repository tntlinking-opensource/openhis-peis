package com.center.medical.pacs.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xhp
 * @since 2023-05-29 14:57
 */
@Data
public class PacsAbteilungSaveScreenshotVo {
    @ApiModelProperty(value = "图片id")
    private String id;
    @ApiModelProperty(value = "图片访问卢了经")
    private String src;
    @ApiModelProperty(value = "是否进报告：0.否 1.是(PACS  彩超控制个检报告和科室报告)")
    private int inReport;
}
