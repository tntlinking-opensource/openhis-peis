package com.center.medical.abteilung.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * KS样本录入(SampleDelivery)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:11
 */
@Data
@TableName("md_sample_delivery")
@ApiModel(value = "SampleDelivery", description = "KS样本录入实体类")
public class SampleDelivery extends Model<SampleDelivery> implements Serializable {
    private static final long serialVersionUID = 545496809200904223L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "创建日期")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "样本编号")
    private Integer sampleNo;

    @ApiModelProperty(value = "交接时间")
    private Date deliveryTime;

    @ApiModelProperty(value = "交接人")
    private String deliveryName;

    @ApiModelProperty(value = "是否合格")
    private Integer isQualified;

    @ApiModelProperty(value = "承接人")
    private String recipient;

    @ApiModelProperty(value = "承接科室ID")
    private String departmentId;

    @ApiModelProperty(value = "不合格原因")
    private String unQualifiedId;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;

    @ApiModelProperty(value = "是否已回访")
    private Integer isVisit;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "关联收费项目表id")
    private String idExamfeeitem;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;
}
