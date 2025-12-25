package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2023-03-21 14:46
 * @description: 团体预约设置列表查询参数
 */
@Data
@ApiModel(value = "预约设置列表查询参数")
public class ReservationSettingGroupParam implements Serializable {
    private static final long serialVersionUID = 589438223479623738L;

    @ApiModelProperty(value = "预约日期", required = true)
    @NotNull(message = "请先选择起始日期!")
    private Date startDate;

    @ApiModelProperty(value = "预约日期", required = true)
    @NotNull(message = "请先选择截至日期!")
    private Date endDate;

    @ApiModelProperty(value = "预约类型")
    private Integer levelId;

    @ApiModelProperty(value = "分中心ID")
    @NotBlank(message = "请先选择预约的分中心!")
    private String branchId;

    @ApiModelProperty(value = "状态")
    private List<Integer> statusList;
}
