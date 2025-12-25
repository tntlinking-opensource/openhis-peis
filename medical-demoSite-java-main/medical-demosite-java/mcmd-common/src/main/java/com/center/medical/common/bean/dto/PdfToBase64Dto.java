package com.center.medical.common.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-04-01 10:54
 * @description: PdfToBase64
 */
@Data
public class PdfToBase64Dto implements Serializable {
    private static final long serialVersionUID = 2411979377430388261L;

    @ApiModelProperty(value = "Base64的图片")
    private String imgBase64;

    @ApiModelProperty(value = "总页数")
    private Integer total;

    @ApiModelProperty(value = "当前页，第一页为1")
    private Integer curPage;

    @ApiModelProperty(value = "请求页数")
    private Integer size;

    public PdfToBase64Dto(String imgBase64, Integer total, Integer curPage, Integer size) {
        this.imgBase64 = imgBase64;
        this.total = total;
        this.curPage = curPage;
        this.size = size;
    }
}
