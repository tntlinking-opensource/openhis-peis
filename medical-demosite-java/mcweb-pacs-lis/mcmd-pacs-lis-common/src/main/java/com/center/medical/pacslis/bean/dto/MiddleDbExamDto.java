package com.center.medical.pacslis.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xhp
 * @since 2023-02-28 11:21
 */
@ApiModel("插入中间库接口固定参数对象-检查项目信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiddleDbExamDto {
    @ApiModelProperty(value = "科室名称")
    private String departName;
    @ApiModelProperty(value = "科室接口类型")
    private String transfterTarget;
    @ApiModelProperty(value = "科室id")
    private Integer idDepart;
    @ApiModelProperty(value = "检查项目名称")
    private String examItemName;
    @ApiModelProperty(value = "检查项目接口代码")
    private String examItemCode;
    @ApiModelProperty(value = "检查项目id")
    private Integer idExamItem;
}
