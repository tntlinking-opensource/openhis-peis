package com.center.medical.appadmin.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 小程序文章上线
 */
@Data
public class GoLiveParam implements Serializable {
    private static final long serialVersionUID = -5793710376127083634L;


    @ApiModelProperty(value = "id集合")
    private List<String> ids;


    @ApiModelProperty(value = "状态 0下线 ,1上线")
    private Integer status;
}
