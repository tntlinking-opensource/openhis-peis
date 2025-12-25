package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询 分享报告统计 参数
 */
@Data
public class ReportSharePageParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -5816256500940860025L;

    @ApiModelProperty(value = "状态 0生效1过期")
    private Integer status;


}
