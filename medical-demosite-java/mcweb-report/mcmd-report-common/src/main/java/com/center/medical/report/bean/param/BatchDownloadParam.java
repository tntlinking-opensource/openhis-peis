package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 批量下载参数
 */
@Data
public class BatchDownloadParam implements Serializable {
    private static final long serialVersionUID = -1168249177570755938L;

    @ApiModelProperty(value = "pdf地址")
    private String urlPdf;

    @ApiModelProperty(value = "命名方式")
    private String namingMethod;

}
