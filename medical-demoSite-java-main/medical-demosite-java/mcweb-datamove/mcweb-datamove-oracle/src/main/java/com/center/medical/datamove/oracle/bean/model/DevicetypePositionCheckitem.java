package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * (DevicetypePositionCheckitem)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:10
 */
@Data
@TableName("DEVICETYPE_POSITION_CHECKITEM")
@ApiModel(value = "DevicetypePositionCheckitem", description = "$tableInfo.comment实体类")
public class DevicetypePositionCheckitem extends Model<DevicetypePositionCheckitem> implements Serializable {
    private static final long serialVersionUID = 137817408463616504L;

    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String devicetypename;

    @ApiModelProperty(value = "${column.comment}")
    private String studypositionname;

    @ApiModelProperty(value = "${column.comment}")
    private String checkitemnname;
}
