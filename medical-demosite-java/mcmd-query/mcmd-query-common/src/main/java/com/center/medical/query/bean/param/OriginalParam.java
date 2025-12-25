package com.center.medical.query.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 科室拒检查询 分页参数
 */
@Data
public class OriginalParam implements Serializable {
    private static final long serialVersionUID = -4734568751019971058L;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;
}
