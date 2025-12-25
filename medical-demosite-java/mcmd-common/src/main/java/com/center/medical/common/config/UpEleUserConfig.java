package com.center.medical.common.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 电测听上传审核人配置
 */
@Data
public class UpEleUserConfig implements Serializable {
    private static final long serialVersionUID = 6679932491080065821L;

    @ApiModelProperty(value = "检查人ID")
    private String rummagerId;

    @ApiModelProperty(value = "检查人姓名(rummager)")
    private String rummagerName;


    @ApiModelProperty(value = "录入人ID")
    private String writeId;

    @ApiModelProperty(value = "录入人姓名")
    private String writeName;


    @ApiModelProperty(value = "审核人ID")
    private String auditId;

    @ApiModelProperty(value = "审核人姓名")
    private String auditName;


}
