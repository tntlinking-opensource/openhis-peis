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
 * 日历信息表(QrtzCalendars)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:30
 */
@Data
@TableName("qrtz_calendars")
@ApiModel(value = "QrtzCalendars", description = "日历信息表实体类")
public class QrtzCalendars extends Model<QrtzCalendars> implements Serializable {
    private static final long serialVersionUID = 935928835602860374L;

    @TableId(value = "sched_name", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "调度名称")
    private String schedName;

    @ApiModelProperty(value = "日历名称")
    private String calendarName;

    @ApiModelProperty(value = "存放持久化calendar对象")
    private Object calendar;
}
