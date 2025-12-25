package com.center.medical.app.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-04-01 10:54
 * @description: 体检者检查项目
 */
@Data
public class ItemDto implements Serializable {
    private static final long serialVersionUID = -188370741633929054L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "是否加项：0:未加项 1：加项")
    private Integer sfjx;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "是否备选：0不是 1是")
    private Integer isbx;
}
