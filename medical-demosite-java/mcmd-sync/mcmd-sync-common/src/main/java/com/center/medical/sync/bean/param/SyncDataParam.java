package com.center.medical.sync.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-02-07 14:40
 * @description: 获取最新同步数据接口参数
 */
@Data
public class SyncDataParam implements Serializable {

    @ApiModelProperty("分中心ID")
    private String brandId;

    @ApiModelProperty("同步业务类型")
    private int optionType;

}
