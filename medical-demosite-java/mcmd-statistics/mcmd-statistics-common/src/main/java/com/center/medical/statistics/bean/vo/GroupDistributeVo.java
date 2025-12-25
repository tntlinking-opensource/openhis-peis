package com.center.medical.statistics.bean.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 体检团体分布情况 分页返回数据
 */
@Data
public class GroupDistributeVo implements Serializable {
    private static final long serialVersionUID = -7354168255718081988L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "数量")
    private String sl;

    @ApiModelProperty(value = "订单名称")
    private String ddmc;
}
