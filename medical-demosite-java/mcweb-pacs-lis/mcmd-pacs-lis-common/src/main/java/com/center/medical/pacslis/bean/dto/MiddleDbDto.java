package com.center.medical.pacslis.bean.dto;

import com.center.medical.common.config.LisConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xhp
 * @since 2023-02-28 11:15
 */
@ApiModel("插入中间库接口固定参数对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiddleDbDto {
    @ApiModelProperty(value = "人员信息")
    private MiddleDbPatientDto middleDbPatientDto;
    @ApiModelProperty(value = "收费项目信息")
    private List<MiddleDbItemDto> middleDbItemDtos;
    @ApiModelProperty(value = "检查项目信息")
    private List<MiddleDbExamDto> middleDbExamDtos;
    @ApiModelProperty(value = "接口类型")
    private List<MiddleDbPatientTransTargetDto> middleDbPatientTransTargetDtos;
    @ApiModelProperty(value = "lis配置")
    private LisConfig lisConfig;
}
