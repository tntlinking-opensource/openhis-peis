package com.center.medical.common.core.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取失效图片数据
 */
@Data
public class InvalidImagesDto implements Serializable {
    private static final long serialVersionUID = 2392867282883176720L;


    @ApiModelProperty(value = "id")
    private Long userId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "签名图片")
    private String signPic;
}
