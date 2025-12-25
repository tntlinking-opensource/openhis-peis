package com.center.medical.pacslis.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xhp
 * @since 2023-02-28 15:43
 */
@ApiModel("插入中间库接口固定参数对象-接口类型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiddleDbPatientTransTargetDto {
    @ApiModelProperty(value = "收费项目名称，多个用,拼接")
    private String feeItemList;
    @ApiModelProperty(value = "科室接口类型")
    private String transfterTarget;
    @ApiModelProperty(value = "标本类型id")
    private Integer idLabType;
    @ApiModelProperty(value = "标本类型名称")
    private String labTypeName;
}
