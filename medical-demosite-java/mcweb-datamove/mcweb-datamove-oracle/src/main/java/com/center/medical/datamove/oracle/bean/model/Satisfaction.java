package com.center.medical.datamove.oracle.bean.model;


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
 * KF满意度(Satisfaction)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:36
 */
@Data
@TableName("SATISFACTION")
@ApiModel(value = "Satisfaction", description = "KF满意度实体类")
public class Satisfaction extends Model<Satisfaction> implements Serializable {
    private static final long serialVersionUID = 626401888635151077L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "科室/前台")
    private Integer divisionReceptionist;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "开单医生ID")
    private String doctorId;

    @ApiModelProperty(value = "评价结果")
    private String appraiseResult;

    @ApiModelProperty(value = "回访结果")
    private Integer visitResult;

    @ApiModelProperty(value = "评价时间")
    private Date appraiseTime;

    @ApiModelProperty(value = "体检号")
    private String personcode;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否已非常满意")
    private Integer isVerySatisfy;

    @ApiModelProperty(value = "评价备注")
    private String note;

    @ApiModelProperty(value = "回访时间")
    private Date visitTime;

    @ApiModelProperty(value = "回访人")
    private String visitPerson;

    @ApiModelProperty(value = "回访备注")
    private String visitNote;

    @ApiModelProperty(value = "回访结果2")
    private Integer secondResult;

    @ApiModelProperty(value = "回访备注2")
    private String secondNote;

    @ApiModelProperty(value = "回访人2")
    private String secondPerson;

    @ApiModelProperty(value = "回访时间2")
    private Date secondTime;

    @ApiModelProperty(value = "假删标识")
    private Double isDelete;

    @ApiModelProperty(value = "体检者姓名")
    private String patientname;

    @ApiModelProperty(value = "跟进内容")
    private String visitText;

    @ApiModelProperty(value = "科室医生ID")
    private String ksDoctorId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer satisfactionLevel;

    @ApiModelProperty(value = "${column.comment}")
    private String sourceType;

    @ApiModelProperty(value = "${column.comment}")
    private String userId;

    @ApiModelProperty(value = "${column.comment}")
    private String consultId;
}
