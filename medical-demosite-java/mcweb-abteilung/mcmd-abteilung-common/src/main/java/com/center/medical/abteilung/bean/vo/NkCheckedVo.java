package com.center.medical.abteilung.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 科室返回保存数据
 */
@Data
public class NkCheckedVo implements Serializable {
    private static final long serialVersionUID = 3967062753893018213L;

    @ApiModelProperty(value = "检查人")
    private String jcr;

    @ApiModelProperty(value = "检查时间")
    private String jcsj;

    @ApiModelProperty(value = "小结")
    private String xj;

    @ApiModelProperty(value = "是否审核")
    private Boolean sfsh;

    @ApiModelProperty(value = "检查人名称")
    private String jcrName;

    @ApiModelProperty(value = "录入人名称")
    private String lrrName;

    @ApiModelProperty(value = "录入时间")
    private String lrsj;

    @ApiModelProperty(value = "录入人")
    private String lrr;

    @ApiModelProperty(value = "单项数据")
    private List<NkCheckedDataVo> data;
}
