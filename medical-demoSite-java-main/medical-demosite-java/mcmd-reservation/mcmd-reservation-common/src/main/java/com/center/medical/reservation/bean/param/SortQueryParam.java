package com.center.medical.reservation.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 项目预约明细 分页参数
 */
@Data
public class SortQueryParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -1969202554116850364L;

    @ApiModelProperty(value = "含个检，1是0否")
    private Integer containSingle;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "检查项目ID")
    private String itemId;
}
