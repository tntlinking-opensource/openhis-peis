package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * KF预检跟踪回访记录表(AdvanceVisit)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:54
 */
@Data
@TableName("md_advance_visit")
@ApiModel(value = "AdvanceVisit", description = "KF预检跟踪回访记录表实体类")
public class AdvanceVisit extends Model<AdvanceVisit> implements Serializable {
    private static final long serialVersionUID = -34688657026241499L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "是否来检：0是 1.否  2再通知 ")
    private Integer isInspect;

    @ApiModelProperty(value = "来检时间")
    private Date inspectTime;

    @ApiModelProperty(value = "回访人ID")
    private String visitId;

    @ApiModelProperty(value = "回访时间")
    private Date visitTime;

    @ApiModelProperty(value = "回访备注")
    private String memo;

    @ApiModelProperty(value = "预检跟踪回访主表ID")
    private String vmId;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;
}
