package com.center.medical.pacs.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xhp
 * @since 2023-03-31 8:05
 */
@Data
public class PacsConclusionVo {

    @ApiModelProperty(value = "结论词id")
    private String id;

    @ApiModelProperty(value = "结论名称")
    private String name;

    @ApiModelProperty(value = "拼音输入码")
    private String inputCode;


}
