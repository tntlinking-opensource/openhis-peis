package com.center.medical.sellcrm.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 导出套餐获取收费项目
 */
@Data
public class CMSfxmDto implements Serializable {
    private static final long serialVersionUID = 8131339445358057841L;

    @ApiModelProperty(value = "打印项目分类名称")
    private String fz;

    @ApiModelProperty(value = "收费项目id")
    private String itemId;

    @ApiModelProperty(value = "收费项目名称")
    private String sfxm;

    @ApiModelProperty(value = "检查目的")
    private String jcmd;

    @ApiModelProperty(value = "价格")
    private String jg;

    @ApiModelProperty(value = "套餐id")
    private String tcId;

    @ApiModelProperty(value = "体检套餐简称")
    private String tcjc;

}
