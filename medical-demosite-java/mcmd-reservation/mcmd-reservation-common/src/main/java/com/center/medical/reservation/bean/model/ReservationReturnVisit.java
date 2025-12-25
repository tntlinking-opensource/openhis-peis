package com.center.medical.reservation.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约回访记录(ReservationReturnVisit)表实体类
 *
 * @author ay
 * @since 2023-03-18 08:54:15
 */
@Data
@TableName("md_reservation_return_visit")
@ApiModel(value = "ReservationReturnVisit", description = "预约回访记录实体类")
public class ReservationReturnVisit extends Model<ReservationReturnVisit> implements Serializable {
    private static final long serialVersionUID = 401256416220251766L;

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "体检者ID")
    private String patientId;

    @ApiModelProperty(value = "回访人ID")
    private String visitId;

    @ApiModelProperty(value = "回访时间")
    private Date visitTime;

    @ApiModelProperty(value = "回访备注")
    private String visitNote;

    @ApiModelProperty(value = "是否来检(1来检0不来)")
    private Integer sflj;

    @ApiModelProperty(value = "来检时间")
    private Date ljsj;
}
