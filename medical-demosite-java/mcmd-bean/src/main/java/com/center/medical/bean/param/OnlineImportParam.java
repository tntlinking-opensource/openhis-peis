package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 线上导入参数
 */
@Data
public class OnlineImportParam implements Serializable {
    private static final long serialVersionUID = 8682438328311047147L;

    @ApiModelProperty(value = "开始时间")
    private String startTime;


    @ApiModelProperty(value = "结束时间")
    private String endTime;


    @ApiModelProperty(value = "分中心id")
    private String fzxId;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "订单号集合")
    private List<String> ddhs;
}
