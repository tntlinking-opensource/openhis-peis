package com.center.medical.pacs.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xhp
 * @since 2023-03-23 14:40
 */
@Data
public class PacsAbteilungImgVo {
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "dicom文件访问路径，高清屏使用，字段组成成分:dicom路径,窗宽,窗位")
    private String dcmsrc;
    @ApiModelProperty(value = "图片访问路径")
    private String src;
    @ApiModelProperty(value = "图片实际路径")
    private String path;
    @ApiModelProperty(value = "是否进报告 1是0否")
    private int inReport;
}
