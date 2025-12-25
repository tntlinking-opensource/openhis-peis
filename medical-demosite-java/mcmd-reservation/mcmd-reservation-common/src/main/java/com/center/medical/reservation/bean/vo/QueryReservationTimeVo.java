package com.center.medical.reservation.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 分页查询预约时间 返回数据
 */
@Data
public class QueryReservationTimeVo implements Serializable {
    private static final long serialVersionUID = -8777353183039823101L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "预约日期")
    private Date reservationDate;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "人数上限")
    private Integer maxNum;

    @ApiModelProperty(value = "剩余可预约人数")
    private Integer ableNum;

    @ApiModelProperty(value = "已预约人数")
    private Integer doneNum;

    @ApiModelProperty(value = "状态：0.关闭 1.开放 2.过期")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "分中心名称")
    private String branchName;

    @ApiModelProperty(value = "预约类型名称")
    private String levelName;

}
