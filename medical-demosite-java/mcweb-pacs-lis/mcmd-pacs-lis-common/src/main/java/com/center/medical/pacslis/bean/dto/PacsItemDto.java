package com.center.medical.pacslis.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xhp
 * @since 2023-03-07 14:51
 */
@Data
public class PacsItemDto {
    @ApiModelProperty(value = "收费项目id")
    private String idExamfeeitem;
    @ApiModelProperty(value = "收费项目接口代码")
    private String examfeeitemCode;
    @ApiModelProperty(value = "科室代码")
    private String idKs;
    @ApiModelProperty(value = "peispatientfeeitem.id")
    private String id;
    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;
    @ApiModelProperty(value = "收费项目打印名称")
    private String examfeeitemNameprn;
}
