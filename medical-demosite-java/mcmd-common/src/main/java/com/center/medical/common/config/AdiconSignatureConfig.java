package com.center.medical.common.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 检验科艾迪康图片签名设置
 */
@Data
public class AdiconSignatureConfig implements Serializable {
    private static final long serialVersionUID = -6455530507562351813L;

    @ApiModelProperty(value = "审核人姓名")
    private String auditName;


    @ApiModelProperty(value = "检查人姓名")
    private String inspectName;


}
