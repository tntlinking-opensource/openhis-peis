package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 设置艾迪康代码参数
 */
@Data
public class SetAdiconParam implements Serializable {
    private static final long serialVersionUID = -4364714496498996388L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "examitemCode3")
    private String examitemCode3;


}
