package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取分页统计数据返回数据
 */
@Data
public class StatisticsVo implements Serializable {
    private static final long serialVersionUID = -4228919192549612069L;

    @ApiModelProperty(value = "已备单")
    private Integer ybd;

    @ApiModelProperty(value = "线上备单")
    private Integer xsbd;

    @ApiModelProperty(value = "未备单")
    private Integer wbd;

    @ApiModelProperty(value = "变更未备单")
    private Integer bgwbd;

}
