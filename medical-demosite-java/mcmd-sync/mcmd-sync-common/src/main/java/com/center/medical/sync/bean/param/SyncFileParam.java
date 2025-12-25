package com.center.medical.sync.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2023-02-07 14:40
 * @description: 图片同步记录查询条件
 */
@Data
@ApiModel(value = "SyncFileParam", description = "图片同步记录查询条件")
public class SyncFileParam implements Serializable {
    private static final long serialVersionUID = -2529422765309585511L;

    @ApiModelProperty(value = "同步到指定中心的ID集合")
    private String branchIds;

    @ApiModelProperty(value = "图片路径，多张以,隔开")
    private String imgUrl;

    @ApiModelProperty(value = "同步状态列表：0.待同步 1.同步中 2.已完成同步 3.同步失败待重新同步 4.失效")
    private List<Integer> statusList;

    public SyncFileParam(String branchIds, String imgUrl, List<Integer> statusList) {
        this.branchIds = branchIds;
        this.imgUrl = imgUrl;
        this.statusList = statusList;
    }
}
