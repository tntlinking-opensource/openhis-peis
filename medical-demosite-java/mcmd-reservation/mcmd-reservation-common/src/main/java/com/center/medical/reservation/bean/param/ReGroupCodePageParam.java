package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 团体预约分享码 分页参数
 */
@Data
public class ReGroupCodePageParam implements Serializable {
    private static final long serialVersionUID = 902051847358019517L;

    @ApiModelProperty(value = "团体ID(团检预约需要)")
    private String idOrg;

}
