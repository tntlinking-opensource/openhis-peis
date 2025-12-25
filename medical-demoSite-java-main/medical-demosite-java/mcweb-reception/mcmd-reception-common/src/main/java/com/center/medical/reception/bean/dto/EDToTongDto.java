package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 当日所有检查统收的返回数据
 */
@Data
public class EDToTongDto implements Serializable {
    private static final long serialVersionUID = 6048759270054924210L;

    @ApiModelProperty(value = "机构名称")
    private String orgName;

    @ApiModelProperty(value = "实付金额")
    private Double paid;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "数字形式团体ID，方便财务导出后处理数据使用")
    private String intId;
}
