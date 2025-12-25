package com.center.medical.pacslis.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xhp
 * @since 2023-02-28 16:24
 */
@ApiModel("插入中间库样本类型数据库结果对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiddleDbYblxListDto {
    @ApiModelProperty(value = "收费项目名称，多个用,拼接")
    private String name;
    @ApiModelProperty(value = "科室接口类型")
    private String jklx;
    @ApiModelProperty(value = "标本类型id")
    private Integer idLabtype;
    @ApiModelProperty(value = "标本类型名称")
    private String labtypeName;
}
