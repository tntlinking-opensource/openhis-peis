package com.center.medical.center.qingdao.profession.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 默认分中心 数据
 */
@Data
public class DefaultBranchDto implements Serializable {
    private static final long serialVersionUID = -3064838842133769353L;

    @ApiModelProperty(value = "分中心id")
    private String branchId;

    @ApiModelProperty(value = "简码")
    private String jm;
}
