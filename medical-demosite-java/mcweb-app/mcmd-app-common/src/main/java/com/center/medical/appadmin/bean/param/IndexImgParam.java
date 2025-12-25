package com.center.medical.appadmin.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class IndexImgParam implements Serializable {
    private static final long serialVersionUID = -2623693129265471250L;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;


    @ApiModelProperty(value = "状态 0关闭1启用")
    private Integer status;
}
