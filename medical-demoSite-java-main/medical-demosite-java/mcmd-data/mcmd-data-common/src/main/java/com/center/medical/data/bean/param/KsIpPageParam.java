package com.center.medical.data.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 科室ip分页参数
 */
@Data
public class KsIpPageParam implements Serializable {
    private static final long serialVersionUID = 38568740497600912L;

    @ApiModelProperty(value = "科室名称")
    private String name;

}
