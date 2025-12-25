package com.center.medical.reservation.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约各检区设置(ReservationSetting)表实体类
 *
 * @author ay
 * @since 2023-03-18 08:54:15
 */
@Data
@TableName("md_reservation_setting")
@ApiModel(value = "ReservationSetting", description = "预约各检区设置实体类")
public class ReservationSetting extends Model<ReservationSetting> implements Serializable {
    private static final long serialVersionUID = -41833119874773886L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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

    @ApiModelProperty(value = "预约类型")
    private Integer levelId;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "上午还是下午：0上午 1下午")
    private Integer amOrPm;

    @ApiModelProperty(value = "状态：0.关闭 1.开放 2.过期")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "创建者id")
    private String creator;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "更新者id")
    private String modifier;

    @ApiModelProperty(value = "分中心名称")
    @TableField(exist = false)
    private String branchName;

    @ApiModelProperty(value = "预约类型名称")
    @TableField(exist = false)
    private String levelName;
}
