package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2023-03-28 11:21
 * @description: 预约列表查询参数
 */
@Data
public class AppointmentParam implements Serializable {
    private static final long serialVersionUID = -8630848541646901332L;

    @ApiModelProperty(value = "预约状态(多个以英文逗号隔开)：-1预约失败 1.待预约 2.已预约 3.申请改期中 4.改期成功 5.申请改期被驳回 6.预约结束 7.取消提交中 8.已取消")
    private List<Integer> statusList;

    /**
     * 用户ID
     */
    @ApiParam(hidden = true)
    private String userId;
}
