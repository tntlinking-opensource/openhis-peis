package com.center.medical.statistics.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 加项工作量 分页参数
 */
@Data
public class TotalAddWorkParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -4567959378524153178L;

    @ApiModelProperty(value = "检验医师id")
    private String doctorId;
}
