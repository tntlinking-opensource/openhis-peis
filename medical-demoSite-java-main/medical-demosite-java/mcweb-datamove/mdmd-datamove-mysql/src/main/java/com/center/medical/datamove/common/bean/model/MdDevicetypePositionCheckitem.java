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
 * 东软pacs部位方法基础表(MdDevicetypePositionCheckitem)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:21
 */
@Data
@TableName("md_devicetype_position_checkitem")
@ApiModel(value = "MdDevicetypePositionCheckitem", description = "东软pacs部位方法基础表实体类")
public class MdDevicetypePositionCheckitem extends Model<MdDevicetypePositionCheckitem> implements Serializable {
    private static final long serialVersionUID = -84993534392104434L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "devicetypename")
    private String devicetypename;

    @ApiModelProperty(value = "studypositionname")
    private String studypositionname;

    @ApiModelProperty(value = "checkitemnname")
    private String checkitemnname;
}
