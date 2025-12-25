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
 * 所属地区(MdBaseZoneQd)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:11
 */
@Data
@TableName("md_base_zone_qd")
@ApiModel(value = "MdBaseZoneQd", description = "所属地区实体类")
public class MdBaseZoneQd extends Model<MdBaseZoneQd> implements Serializable {
    private static final long serialVersionUID = 144520088634161910L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "区域代码")
    private String zoneCode;

    @ApiModelProperty(value = "区域名称")
    private String zoneName;

    @ApiModelProperty(value = "青岛区域代码")
    private String qingdaoZoneCode;

    @ApiModelProperty(value = "青岛区域名称")
    private String qingdaoZoneName;
}
