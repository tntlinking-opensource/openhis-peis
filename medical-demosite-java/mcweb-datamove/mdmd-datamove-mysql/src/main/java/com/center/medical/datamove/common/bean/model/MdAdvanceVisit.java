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
 * KF预检跟踪回访记录表(MdAdvanceVisit)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:05
 */
@Data
@TableName("md_advance_visit")
@ApiModel(value = "MdAdvanceVisit", description = "KF预检跟踪回访记录表实体类")
public class MdAdvanceVisit extends Model<MdAdvanceVisit> implements Serializable {
    private static final long serialVersionUID = 829018379677832629L;

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
    private Date createdate;

    @ApiModelProperty(value = "更改时间")
    private Date modifydate;
}
