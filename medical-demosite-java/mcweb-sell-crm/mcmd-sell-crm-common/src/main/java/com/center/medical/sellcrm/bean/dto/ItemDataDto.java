package com.center.medical.sellcrm.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通过sql获取相关的收费项目
 */
@Data
public class ItemDataDto implements Serializable {
    private static final long serialVersionUID = 566668170652470031L;

    @ApiModelProperty(value = "打印名称")
    private String fz;

    @ApiModelProperty(value = "检查项目ids")
    private String itemids;

    @ApiModelProperty(value = "收费项目")
    private String sfxms;

    @ApiModelProperty(value = "检查名称")
    private String jcmds;

    @ApiModelProperty(value = "价格")
    private String jgs;

    @ApiModelProperty(value = "套餐id")
    private String tcids;

    @ApiModelProperty(value = "套餐简称")
    private String tcjcs;

}
