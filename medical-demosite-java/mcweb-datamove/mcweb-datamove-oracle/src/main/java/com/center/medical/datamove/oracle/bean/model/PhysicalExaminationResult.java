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
 * 体检结果表（处理后）(PhysicalExaminationResult)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:35
 */
@Data
@TableName("PHYSICAL_EXAMINATION_RESULT")
@ApiModel(value = "PhysicalExaminationResult", description = "体检结果表（处理后）实体类")
public class PhysicalExaminationResult extends Model<PhysicalExaminationResult> implements Serializable {
    private static final long serialVersionUID = -74644362484651840L;

    @ApiModelProperty(value = "修改日期")
    private Date modifyDate;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "体检日期")
    private String ndate;

    @ApiModelProperty(value = "检查时间 * 格式 HHmm")
    private String ntime;

    @ApiModelProperty(value = "体检号")
    private String patindex;

    @ApiModelProperty(value = "体检者姓名")
    private String patname;

    @ApiModelProperty(value = "体检者性别")
    private String patsex;

    @ApiModelProperty(value = "lis样本编号")
    private String specimenno;

    @ApiModelProperty(value = "specimennotes")
    private String specimennotes;

    @ApiModelProperty(value = "labelindex")
    private String labelindex;

    @ApiModelProperty(value = "检查医师CODE")
    private String docterindex;

    @ApiModelProperty(value = "检查项目CODE")
    private String testindex;

    @ApiModelProperty(value = "检查结果（数字）")
    private Double nvalue;

    @ApiModelProperty(value = "检查结果（字符）")
    private String svalue;

    @ApiModelProperty(value = "参考值")
    private String reviewnotes;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "检查项目名称")
    private String name;

    @ApiModelProperty(value = "单位")
    private String units;

    @ApiModelProperty(value = "报告范围 低")
    private String reflow;

    @ApiModelProperty(value = "报告范围 高")
    private String refhigh;

    @ApiModelProperty(value = "LIS代码")
    private String prtorder;

    @ApiModelProperty(value = "医师名称")
    private String docname;

    @ApiModelProperty(value = "审核人名称")
    private String rename;

    @ApiModelProperty(value = "检查人代码")
    private String patext3;

    @ApiModelProperty(value = "检查人名称")
    private String aspatname;

    @ApiModelProperty(value = "审核日期")
    private String reviewdate;

    @ApiModelProperty(value = "审核时间")
    private String reviewtime;

    @ApiModelProperty(value = "收样日期")
    private String receivedate;

    @ApiModelProperty(value = "收样时间")
    private String receivetime;

    @ApiModelProperty(value = "数据来源")
    private String source;

    @ApiModelProperty(value = "系统内接口代号")
    private String systemItemCode;
}
