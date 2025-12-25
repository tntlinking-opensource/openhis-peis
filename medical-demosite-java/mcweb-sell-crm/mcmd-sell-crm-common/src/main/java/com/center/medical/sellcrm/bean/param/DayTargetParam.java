package com.center.medical.sellcrm.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 日目标分页参数
 */
@Data
public class DayTargetParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 5290278847808308975L;

    @ApiModelProperty(value = "用户编号,不用传")
    private String userNo;

}
