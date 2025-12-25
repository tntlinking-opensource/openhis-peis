package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 主表记录体检者是否来检，如果来检，显示来检时间，便于筛选，在预约来检未检列表中显示来检时间且是否已检为未检的做出标记，在预约来检未检回访操作时将该状态、来检时间重置，以此进行无限循环(AdvanceVisitWrite)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:54
 */
@Data
@TableName("md_advance_visit_write")
@ApiModel(value = "AdvanceVisitWrite", description = "主表记录体检者是否来检，如果来检，显示来检时间，便于筛选，在预约来检未检列表中显示来检时间且是否已检为未检的做出标记，在预约来检未检回访操作时将该状态、来检时间重置，以此进行无限循环实体类")
public class AdvanceVisitWrite extends Model<AdvanceVisitWrite> implements Serializable {
    private static final long serialVersionUID = 322789038833209967L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

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

    @ApiModelProperty(value = "关联档案表档案号")
    private String patientarchivenoId;

    @ApiModelProperty(value = "是否已检（在预约时间内是否已检）：0是 1.否")
    private Integer isInspected;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;


    @TableField(exist = false)
    @ApiModelProperty(value = "回访备注")
    private String memos;

    @TableField(exist = false)
    @ApiModelProperty(value = "预约来检时间")
    private Date inspectTimes;

    @TableField(exist = false)
    @ApiModelProperty(value = "是否来检")
    private String isInspects;
}
