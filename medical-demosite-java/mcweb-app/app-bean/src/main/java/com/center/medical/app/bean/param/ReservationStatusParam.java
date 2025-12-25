package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-04-04 11:35
 * @description: 预约状态更新参数
 */
@Data
public class ReservationStatusParam implements Serializable {

    private static final long serialVersionUID = 8213475558606116858L;
    
    @ApiModelProperty(value = "预约ID")
    @NotNull(message = "预约ID不能为空！")
    private String id;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "预约状态：-1预约失败 1.待预约 2.已预约 3.申请改期中 4.改期成功 5.申请改期被驳回 6.预约结束 7.取消提交中 8.已取消")
    @NotNull(message = "状态不能为空！")
    private Integer status;
}
