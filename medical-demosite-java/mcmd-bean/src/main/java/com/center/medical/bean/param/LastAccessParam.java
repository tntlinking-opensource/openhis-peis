package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 分享报告-最后访问-次数加一
 */
@Data
public class LastAccessParam implements Serializable {
    private static final long serialVersionUID = 8902996127319160330L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "最后一次访问ip")
    private String lastIp;

    @ApiModelProperty(value = "最后一次访问时间")
    private Date lastTime;
}
