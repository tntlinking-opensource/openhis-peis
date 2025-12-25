package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * KF生日回访表(Memberbirthdat)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:04
 */
@Data
@TableName("MEMBERBIRTHDAT")
@ApiModel(value = "Memberbirthdat", description = "KF生日回访表实体类")
public class Memberbirthdat extends Model<Memberbirthdat> implements Serializable {
    private static final long serialVersionUID = 584669516040619547L;

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
