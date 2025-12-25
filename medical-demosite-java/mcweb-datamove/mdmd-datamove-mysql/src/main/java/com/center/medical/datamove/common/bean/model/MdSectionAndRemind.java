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
 * 科室提醒和科室关联表(MdSectionAndRemind)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:30
 */
@Data
@TableName("md_section_and_remind")
@ApiModel(value = "MdSectionAndRemind", description = "科室提醒和科室关联表实体类")
public class MdSectionAndRemind extends Model<MdSectionAndRemind> implements Serializable {
    private static final long serialVersionUID = 656055958656832243L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "科室提醒主表ID")
    private String remindId;

    @ApiModelProperty(value = "被提醒科室ID")
    private String depId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;
}
