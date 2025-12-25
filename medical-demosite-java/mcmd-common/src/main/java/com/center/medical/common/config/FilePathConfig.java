/*
 * Copyright (c) 2022-2999 青岛沃德互联网医疗科技有限公司 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.common.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 文件存储配置信息
 *{"commonPicturePath":"images/common","imagePicturePath":"images/image","avatorPicturePath":"images/avator","materialFilePath":"files/material","sendPicturePath":"images/send","pactFilePath":"files/pact"}
 * @author 路飞
 */
@Data
public class FilePathConfig implements Serializable {

    private static final long serialVersionUID = 1715326842065644625L;

    /**
     * 公共图片路径
     */
    @ApiModelProperty(value = "公共图片路径")
    private String commonPicturePath;

    /**
     * 影像科室图片路径
     */
    @ApiModelProperty(value = "影像科室图片路径")
    private String imagePicturePath;

    /**
     * 体检者头像路径
     */
    @ApiModelProperty(value = "体检者头像路径")
    private String avatorPicturePath;

    /**
     * 材料文件路径
     */
    @ApiModelProperty(value = "材料文件路径")
    private String materialFilePath;

    /**
     * 外送结果图片路径
     */
    @ApiModelProperty(value = "外送结果图片路径")
    private String sendPicturePath;

    /**
     * 合同文件路径
     */
    @ApiModelProperty(value = "合同文件路径")
    private String pactFilePath;


    /**
     * 瑞林萨尔文件路径
     */
    @ApiModelProperty(value = "瑞林萨尔文件路径")
    private String riLinFilePath;

}
