package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 手机报告查询详情参数
 */
@Data
public class MobileDetailsParam implements Serializable {
    private static final long serialVersionUID = -837137497145367098L;


    @ApiModelProperty(value = "手机号")
    private String phone;


    @ApiModelProperty(value = "id")
    private String id;
}
