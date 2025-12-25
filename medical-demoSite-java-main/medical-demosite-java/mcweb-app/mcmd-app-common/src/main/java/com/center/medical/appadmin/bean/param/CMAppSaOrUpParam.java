package com.center.medical.appadmin.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建套餐添加或修改参数
 */
@Data
public class CMAppSaOrUpParam implements Serializable {
    private static final long serialVersionUID = 4534861646697618960L;

    @ApiModelProperty(value = "ID")
    private String id;


    @ApiModelProperty(value = "套餐ID")
    private String tcid;


    @ApiModelProperty(value = "类型")
    private Integer type;


    @ApiModelProperty(value = "缩略图")
    private String thumbnail;


    @ApiModelProperty(value = "标签名,用英文逗号隔开")
    private String label;


    @ApiModelProperty(value = "图片")
    private String imgUrl;


    @ApiModelProperty(value = "内容")
    private String content;


    @ApiModelProperty(value = "备注")
    private String bz;


    @ApiModelProperty(value = "顺序")
    private Integer seq;

    @ApiModelProperty(value = "小程序套餐名称")
    private String appTcmc;

}
