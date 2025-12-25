package com.center.medical.framework.event;

import com.center.medical.sync.bean.model.SyncData;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: 路飞
 * @date: 2022-11-17 20:38
 * @description: 数据同步事件
 */
@Data
@AllArgsConstructor
public class SyncDataEvent {

    @ApiModelProperty(value = "数据同步信息")
    private SyncData syncData;
}
