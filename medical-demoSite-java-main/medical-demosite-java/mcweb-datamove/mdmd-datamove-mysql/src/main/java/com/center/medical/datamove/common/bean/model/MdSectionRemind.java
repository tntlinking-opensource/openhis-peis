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
 * 科室提醒主表(MdSectionRemind)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:31
 */
@Data
@TableName("md_section_remind")
@ApiModel(value = "MdSectionRemind", description = "科室提醒主表实体类")
public class MdSectionRemind extends Model<MdSectionRemind> implements Serializable {
    private static final long serialVersionUID = 477293261562482905L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "提醒内容")
    private String remindContent;

    @ApiModelProperty(value = "提醒科室ID")
    private String depId;

    @ApiModelProperty(value = "提醒科室名称")
    private String depName;

    @ApiModelProperty(value = "提醒人ID")
    private String userId;

    @ApiModelProperty(value = "提醒人姓名")
    private String userName;

    @ApiModelProperty(value = "提醒时间")
    private Date remindTime;

    @ApiModelProperty(value = "被提醒的科室ids")
    private String depIds;
}
