package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询清除附件表中的特殊符号 返回数据
 */
@Data
public class SpecialSymbolsDataDto implements Serializable {
    private static final long serialVersionUID = -2796940758058527947L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "文件路径")
    private String filePath;

    @ApiModelProperty(value = "体检号")
    private String patientcode;
}
