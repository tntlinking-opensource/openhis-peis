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
 * 所属地区(MdBaseZone)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:10
 */
@Data
@TableName("md_base_zone")
@ApiModel(value = "MdBaseZone", description = "所属地区实体类")
public class MdBaseZone extends Model<MdBaseZone> implements Serializable {
    private static final long serialVersionUID = -94648297942302850L;

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

    @ApiModelProperty(value = "区域级别：1.省 2.市 3.区 4.街道")
    private Integer zoneLevel;

    @ApiModelProperty(value = "上级区域代码 省没有上级区域")
    private String pcode;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "青岛行政区代码")
    private String qingdaoCode;

    @ApiModelProperty(value = "青岛行政编码父级CODE")
    private String qingdaoPcode;
}
