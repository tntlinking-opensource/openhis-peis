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
 * 会员生日提醒回访表(MdMemberbirthdat)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:26
 */
@Data
@TableName("md_memberbirthdat")
@ApiModel(value = "MdMemberbirthdat", description = "会员生日提醒回访表实体类")
public class MdMemberbirthdat extends Model<MdMemberbirthdat> implements Serializable {
    private static final long serialVersionUID = 146105721085084013L;

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
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "跟进内容")
    private String visitText;

    @ApiModelProperty(value = "回访状态")
    private Integer visitStatus;
}
