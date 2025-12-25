package com.center.medical.sync.bean.param;

import com.center.medical.sync.bean.dto.SyncDataDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2023-02-07 14:40
 * @description: 接收线下推送的同步数据
 */
@Data
public class OffLineSyncDataParam implements Serializable {
    private static final long serialVersionUID = -4033373401314052277L;

    @ApiModelProperty(value = "分中心ID")
    private String cid;

    @ApiModelProperty(value = "同步数据集合")
    private List<SyncDataDto> syncDataList;

    public OffLineSyncDataParam(String cid, List<SyncDataDto> syncDataList) {
        this.cid = cid;
        this.syncDataList = syncDataList;
    }
}
