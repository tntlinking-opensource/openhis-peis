package com.center.medical.pacslis.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xhp
 * @since 2023-02-20 16:20
 */
@Data
public class LisItemDto {
    @ApiModelProperty(value = "收费项目id")
    private String idExamfeeitem;
    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;
    @ApiModelProperty(value = "收费项目接口代码")
    private String examfeeitemCode;
    @ApiModelProperty(value = "收费项目打印名称")
    private String examfeeitemNameprn;
    @ApiModelProperty(value = "是否隐私项目 1：是")
    private String bgdybs;
    @ApiModelProperty(value = "艾迪康代码")
    private String adiconCode;
}
