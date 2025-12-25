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
 * KF回访记录表(VisitWrite)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:54
 */
@Data
@TableName("VISIT_WRITE")
@ApiModel(value = "VisitWrite", description = "KF回访记录表实体类")
public class VisitWrite extends Model<VisitWrite> implements Serializable {
    private static final long serialVersionUID = 704208354512373597L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "满意度ID")
    private String satisfactionId;

    @ApiModelProperty(value = "回访结果")
    private String visitResult;

    @ApiModelProperty(value = "0:不满意回访；1：再次不满意回访")
    private Integer visitLevel;

    @ApiModelProperty(value = "回访人ID")
    private String visitterId;

    @ApiModelProperty(value = "回访时间")
    private Date visitTime;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "备注")
    private String memo;
}
