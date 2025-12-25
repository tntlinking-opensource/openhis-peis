package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 所属地区(BaseZone)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:55
 */
@Data
@TableName("md_base_zone")
@ApiModel(value = "BaseZone", description = "所属地区实体类")
public class BaseZone extends Model<BaseZone> implements Serializable {
    private static final long serialVersionUID = 477223777212444677L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
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
