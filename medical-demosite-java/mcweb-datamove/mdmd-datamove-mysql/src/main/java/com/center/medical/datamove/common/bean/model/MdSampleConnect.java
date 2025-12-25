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
 * KS样本交接(MdSampleConnect)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:23
 */
@Data
@TableName("md_sample_connect")
@ApiModel(value = "MdSampleConnect", description = "KS样本交接实体类")
public class MdSampleConnect extends Model<MdSampleConnect> implements Serializable {
    private static final long serialVersionUID = 288703158599284624L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;

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
