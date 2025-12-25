package com.center.medical.pacs.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 彩超 反审核 参数
 */
@Data
public class PascReverseParam implements Serializable {
    private static final long serialVersionUID = -7985451045597047935L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "PACS体检者收费项目ID")
    private String feeitemId;

    @ApiModelProperty("用户名称")
    private String username;


}
