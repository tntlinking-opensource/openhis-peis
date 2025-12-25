package com.center.medical.system.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 客户转移查询参数
 */
@Data
public class XsryDataParam implements Serializable {
    private static final long serialVersionUID = -7476068398916358906L;

    @ApiModelProperty(value = "销售人员名称")
    private String key;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "名称(销售部)")
    private String name;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "分中心")
    private String cid;
}
