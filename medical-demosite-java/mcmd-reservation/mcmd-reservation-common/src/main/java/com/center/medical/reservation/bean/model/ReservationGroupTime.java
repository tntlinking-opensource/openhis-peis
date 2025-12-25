package com.center.medical.reservation.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 团体预约和预约设置关联表(ReservationGroupTime)表实体类
 *
 * @author ay
 * @since 2024-04-26 13:58:00
 */
@Data
@TableName("md_reservation_group_time")
@ApiModel(value = "ReservationGroupTime", description = "团体预约和预约设置关联表实体类")
public class ReservationGroupTime extends Model<ReservationGroupTime> implements Serializable {
    private static final long serialVersionUID = 339102984413456325L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;


    @ApiModelProperty(value = "团体预约id")
    private String groupId;


    @ApiModelProperty(value = "预约时间段ID")
    private String timeId;


    @ApiModelProperty(value = "预约人数")
    private Integer count;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

}
