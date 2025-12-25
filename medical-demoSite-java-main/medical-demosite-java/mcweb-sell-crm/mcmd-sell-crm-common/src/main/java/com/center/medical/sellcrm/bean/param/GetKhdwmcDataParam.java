package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取客户单位数据参数
 */
@Data
public class GetKhdwmcDataParam implements Serializable {
    private static final long serialVersionUID = -3179600446953984076L;


    @ApiModelProperty(value = "筛选条件")
    private String key;
}
