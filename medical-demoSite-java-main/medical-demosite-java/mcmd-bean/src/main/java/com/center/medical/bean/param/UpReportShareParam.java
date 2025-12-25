package com.center.medical.bean.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 更新分享报告参数
 */
@Data
public class UpReportShareParam implements Serializable {
    private static final long serialVersionUID = -6975363162998497325L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "链接名称")
    private String pathName;

    @ApiModelProperty(value = "有效期 7,14,30,999")
    private Integer expirationDate;

    @ApiModelProperty(value = "自动填充分享码 0否1是")
    private Integer autofill;

}
