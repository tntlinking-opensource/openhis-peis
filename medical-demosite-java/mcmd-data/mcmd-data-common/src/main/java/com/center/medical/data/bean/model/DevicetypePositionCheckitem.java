package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 东软pacs部位方法基础表(DevicetypePositionCheckitem)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:58
 */
@Data
@TableName("md_devicetype_position_checkitem")
@ApiModel(value = "DevicetypePositionCheckitem", description = "东软pacs部位方法基础表实体类")
public class DevicetypePositionCheckitem extends Model<DevicetypePositionCheckitem> implements Serializable {
    private static final long serialVersionUID = 221747768124970158L;

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
