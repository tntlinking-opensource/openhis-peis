/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.bean.app.dto;

import com.center.medical.app.common.serializer.json.ImgJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 路飞
 */
@Data
@ApiModel("首页图片对象")
public class IndexImgDto {

    /**
     * 图片
     */
    @JsonSerialize(using = ImgJsonSerializer.class)
    @ApiModelProperty(value = "图片Url", required = true)
    private String imgUrl;

    /**
     * 顺序
     */
    @ApiModelProperty(value = "图片顺序", required = true)
    private Integer seq;

    /**
     * 上传时间
     */
    @ApiModelProperty(value = "上传时间", required = true)
    private Date uploadTime;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", required = true)
    private int type;

    /**
     * 图片类型 0:小程序 1:pc
     */
    @ApiModelProperty(value = "图片类型 0:小程序 1:pc", required = true)
    private int imgType;

    /**
     * 关联id
     */
    @ApiModelProperty(value = "关联id", required = true)
    private Long relation;


}
