package com.center.medical.abteilung.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 科室提醒和科室关联表(SectionAndRemind)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:16
 */
@Data
@TableName("md_section_and_remind")
@ApiModel(value = "SectionAndRemind", description = "科室提醒和科室关联表实体类")
public class SectionAndRemind extends Model<SectionAndRemind> implements Serializable {
    private static final long serialVersionUID = 140168326412073393L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "科室提醒主表ID")
    private String remindId;

    @ApiModelProperty(value = "被提醒科室ID")
    private String depId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;


    public SectionAndRemind() {

    }
    public SectionAndRemind(String patientcode, String depId, String remindId) {
        super();
        this.patientcode = patientcode;
        this.depId = depId;
        this.remindId = remindId;
    }
}
