package com.center.medical.sync.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-02-07 14:40
 * @description: 同步操作的结果
 */
@Data
public class SyncResult implements Serializable {
    private static final long serialVersionUID = 9118943125719972065L;

    @ApiModelProperty(value = "操作ID(md_sync_data表中的ID)")
    private Long syncDataId;

    @ApiModelProperty(value = "操作结果：0.失败 1.成功")
    private Integer result;

    @ApiModelProperty(value = "分中心ID")
    private String cid;
}
