package com.center.medical.reservation.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约信息变更通知记录(ReservationNotify)表实体类
 *
 * @author makejava
 * @since 2023-09-21 10:56:58
 */
@Data
@TableName("md_reservation_notify")
@ApiModel(value = "ReservationNotify", description = "预约信息变更通知记录实体类")
public class ReservationNotify extends Model<ReservationNotify> implements Serializable {
    private static final long serialVersionUID = -95732637288555191L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "预约记录ID")
    private String bizId;

    @ApiModelProperty(value = "系统ID")
    private String systemId;

    @ApiModelProperty(value = "状态：0.待通知 1.已通知")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;
}
