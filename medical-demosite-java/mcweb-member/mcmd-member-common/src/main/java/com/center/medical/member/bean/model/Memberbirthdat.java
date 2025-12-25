package com.center.medical.member.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员生日提醒回访表(Memberbirthdat)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-24 09:59:56
 */
@Data
@TableName("md_memberbirthdat")
@ApiModel(value = "Memberbirthdat", description = "会员生日提醒回访表实体类")
public class Memberbirthdat extends Model<Memberbirthdat> implements Serializable {
    private static final long serialVersionUID = 103806805225352497L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "会员档案ID")
    private String hyId;

    @ApiModelProperty(value = "回访人ID")
    private String visitMan;

    @ApiModelProperty(value = "回访时间")
    private Date visitTime;

    @ApiModelProperty(value = "回访方式")
    private String visitType;

    @ApiModelProperty(value = "回访备注")
    private String visitNote;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "跟进内容")
    private String visitText;

    @ApiModelProperty(value = "回访状态")
    private Integer visitStatus;
}
