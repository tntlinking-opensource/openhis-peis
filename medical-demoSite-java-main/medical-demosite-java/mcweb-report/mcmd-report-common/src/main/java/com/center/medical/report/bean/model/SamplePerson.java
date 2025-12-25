package com.center.medical.report.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * TJ团检报告人员样本表(SamplePerson)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:11
 */
@Data
@TableName("md_sample_person")
@ApiModel(value = "SamplePerson", description = "TJ团检报告人员样本表实体类")
public class SamplePerson extends Model<SamplePerson> implements Serializable {
    private static final long serialVersionUID = 247114144793378418L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "团检报告ID")
    private String ballCheckId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "团体ID")
    private String groupId;

    @ApiModelProperty(value = "创建日期")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "体检者表id")
    private String patientId;
}
