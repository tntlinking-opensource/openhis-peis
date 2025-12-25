package com.center.medical.reservation.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约各检区默认设置(ReservationDefault)表实体类
 *
 * @author ay
 * @since 2023-03-18 08:54:14
 */
@Data
@TableName("md_reservation_default")
@ApiModel(value = "ReservationDefault", description = "预约各检区默认设置实体类")
public class ReservationDefault extends Model<ReservationDefault> implements Serializable {
    private static final long serialVersionUID = 101521406135184264L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "时间段开始时间")
    private String startTime;

    @ApiModelProperty(value = "时间段结束时间")
    private String endTime;

    @ApiModelProperty(value = "人数上限")
    private Integer maxNum;

    @ApiModelProperty(value = "预约类型")
    private Integer levelId;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "开放日，周一至周天，如：1,3,5,7 表示开放时间是周一三五七，二四六不开放")
    private String openDay;

    @ApiModelProperty(value = "上午还是下午：0上午 1下午")
    private Integer amOrPm;

    @ApiModelProperty(value = "状态：0.关闭 1.开放")
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "创建者id")
    private String creator;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
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
