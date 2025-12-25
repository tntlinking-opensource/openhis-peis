package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 预约回访记录(MdReservationReturnVisit)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:18
 */
@Data
@TableName("md_reservation_return_visit")
@ApiModel(value = "MdReservationReturnVisit", description = "预约回访记录实体类")
public class MdReservationReturnVisit extends Model<MdReservationReturnVisit> implements Serializable {
    private static final long serialVersionUID = 558241810152982072L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
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
