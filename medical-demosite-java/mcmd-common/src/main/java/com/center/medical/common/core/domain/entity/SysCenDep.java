package com.center.medical.common.core.domain.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 分中心部门表(SysCenDep)表实体类
 *
 * @author ay
 * @since 2023-04-26 08:43:56
 */
@Data
@TableName("sys_cen_dep")
@ApiModel(value = "SysCenDep", description = "分中心部门表实体类")
public class SysCenDep extends Model<SysCenDep> implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "分中心id")
    private String cid;

    @ApiModelProperty(value = "部门id")
    private String did;

    @ApiModelProperty(value = "头像地址")
    private String imgpath;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "开始时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "序号")
    private String xh;

    @ApiModelProperty(value = "数据报告格式")
    private String sjbggs;

    @ApiModelProperty(value = "检查地点")
    private String jcdd;

    @ApiModelProperty(value = "接口类型")
    private String jklx;

    @ApiModelProperty(value = "科室号")
    private String ksh;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "导引单序号")
    private String dydXh;

    @ApiModelProperty(value = "${column.comment}")
    private String dydMemo;
}
