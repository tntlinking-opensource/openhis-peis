package com.center.medical.datamove.oracle.bean.model;


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
 * 所属地区(BaseZoneQd)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:41
 */
@Data
@TableName("BASE_ZONE_QD")
@ApiModel(value = "BaseZoneQd", description = "所属地区实体类")
public class BaseZoneQd extends Model<BaseZoneQd> implements Serializable {
    private static final long serialVersionUID = -19073589814013489L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "区域代码")
    private String zoneCode;

    @ApiModelProperty(value = "区域名称")
    private String zoneName;

    @ApiModelProperty(value = "${column.comment}")
    private String qingdaoZoneCode;

    @ApiModelProperty(value = "${column.comment}")
    private String qingdaoZoneName;
}
