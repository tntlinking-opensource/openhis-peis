package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 生成分享码 需要参数
 */
@Data
public class GenerateGroupCodeParam implements Serializable {
    private static final long serialVersionUID = 4867849091361227418L;

    @ApiModelProperty(value = "分组id")
    private String groupId;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;


}
