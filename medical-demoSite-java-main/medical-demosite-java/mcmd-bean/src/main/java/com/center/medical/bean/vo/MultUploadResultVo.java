package com.center.medical.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 路飞船长
 * @date: 2023/4/21 06:36
 * @description: 多文件上传结果
 */
@Data
@ApiModel(value = "多文件上传结果")
public class MultUploadResultVo implements Serializable {
    private static final long serialVersionUID = -4788366016929419324L;

    @ApiModelProperty("上传成功的图片地址")
    private List<String> urlList;

    @ApiModelProperty("上传成功的图片名称：和urlList一一对应")
    private List<String> successList;

    @ApiModelProperty("上传失败的图片名称")
    private List<String> failList;

    @ApiModelProperty("上传结果描述")
    private String resultMsg;
}
