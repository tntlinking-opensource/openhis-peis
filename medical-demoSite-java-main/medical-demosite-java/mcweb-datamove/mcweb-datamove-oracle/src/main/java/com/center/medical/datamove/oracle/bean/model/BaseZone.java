package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 所属地区(BaseZone)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:40
 */
@Data
@TableName("BASE_ZONE")
@ApiModel(value = "BaseZone", description = "所属地区实体类")
public class BaseZone extends Model<BaseZone> implements Serializable {
    private static final long serialVersionUID = -98184971336791731L;

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

    @ApiModelProperty(value = "区域级别  1省 2市 3区 4街道")
    private Integer zoneLevel;

    @ApiModelProperty(value = "上级区域代码 省没有上级区域")
    private String pcode;

    @ApiModelProperty(value = "删除标志  1已删除 0未删除")
    private Integer isDelete;

    @ApiModelProperty(value = "青岛行政区代码 ")
    private String qingdaoCode;

    @ApiModelProperty(value = "青岛行政编码父级CODE")
    private String qingdaoPcode;
}
