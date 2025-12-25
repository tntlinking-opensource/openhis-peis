package com.center.medical.statistics.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 右侧体检者数据 参数
 */
@Data
public class AnalyseInfoParam implements Serializable {
    private static final long serialVersionUID = -8931147595223024828L;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "检查项目ID")
    private String itemId;

    @ApiModelProperty(value = "医师")
    private String doctor;

    @ApiModelProperty(value = "0：未检；1：已检；")
    private Integer fExaminated;
}
