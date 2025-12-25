package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取经济类型
 */
@Data
public class CrptSizeCodeVo implements Serializable {
    private static final long serialVersionUID = -988589429475738056L;


    @ApiModelProperty(value = "编码")
    private String crptSizeCode;

    @ApiModelProperty(value = "名称")
    private String crptSizeName;
}
