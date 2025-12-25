/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.center.medical.app.common.serializer.json.ImgJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Yami
 */
@Data
@TableName("md_index_img")
public class IndexImg implements Serializable {
    private static final long serialVersionUID = -3468251351681518798L;
    /**
     * 主键
     */
    @TableId(value = "img_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("轮播图id")
    private Long imgId;

    @ApiModelProperty("分中心ID")
    private Long branchId;

    @ApiModelProperty("图片")
    private String imgUrl;

    @ApiModelProperty("描述")
    private String des;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("链接")
    private String link;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("顺序")
    private Integer seq;

    @ApiModelProperty("上传时间")
    private Date uploadTime;

    @ApiModelProperty("类型")
    private int type;

    @ApiModelProperty("图片类型 0:小程序 1:小程序")
    private Integer imgType;

    @ApiModelProperty("关联id")
    private Long relation;

    @ApiModelProperty("商品图片")
    @TableField(exist = false)
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String pic;

    @ApiModelProperty("商品名称")
    @TableField(exist = false)
    private String prodName;

}
