package com.center.medical.abteilung.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 科室提醒主表(SectionRemind)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:17
 */
@Data
@TableName("md_section_remind")
@ApiModel(value = "SectionRemind", description = "科室提醒主表实体类")
public class SectionRemind extends Model<SectionRemind> implements Serializable {
    private static final long serialVersionUID = -61790670983974046L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
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


    public SectionRemind(String patientcode, String remindContent, String depId,
                         String depName, String userId, String userName, Date remindTime,String depIds) {
        super();
        this.patientcode = patientcode;
        this.remindContent = remindContent;
        this.depId = depId;
        this.depName = depName;
        this.userId = userId;
        this.userName = userName;
        this.remindTime = remindTime;
        this.depIds=depIds;
    }

    public SectionRemind() {

    }

}
