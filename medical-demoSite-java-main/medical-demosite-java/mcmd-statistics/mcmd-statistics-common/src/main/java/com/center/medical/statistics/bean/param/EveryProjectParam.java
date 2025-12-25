package com.center.medical.statistics.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 每日体检项目统计
 */
@Data
public class EveryProjectParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -5300047881147049058L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "科室")
    private String ks;

    @ApiModelProperty(value = "0：未检；1：已检；")
    private Integer FExaminated;

}
