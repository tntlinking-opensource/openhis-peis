package com.center.medical.sellcrm.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 销售月度目标分页参数
 */
@Data
public class MonthtargetParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 7017035238920353303L;

    @ApiModelProperty(value = "年")
    private String listYear;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户id")
    private String userNo;
}
