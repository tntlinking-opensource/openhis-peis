package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 对比报告表，用于存储对报告都有哪些生成过(CompareReport)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:17:45
 */
@Data
@TableName("COMPARE_REPORT")
@ApiModel(value = "CompareReport", description = "对比报告表，用于存储对报告都有哪些生成过实体类")
public class CompareReport extends Model<CompareReport> implements Serializable {
    private static final long serialVersionUID = -96697503205012989L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "档案号")
    private String patientarchiveno;

    @ApiModelProperty(value = "本届体检号")
    private String patientcodeThis;

    @ApiModelProperty(value = "上届体检号")
    private String patientcodeBefore;

    @ApiModelProperty(value = "上二届体检号")
    private String patientcodeSecond;

    @ApiModelProperty(value = "本届登记时间")
    private Date registerThis;

    @ApiModelProperty(value = "上届登记时间")
    private Date registerBefore;

    @ApiModelProperty(value = "上二届登记时间")
    private Date registerSecond;

    @ApiModelProperty(value = "对比报告存放位置（WORD）")
    private String pathWord;

    @ApiModelProperty(value = "对比报告存放位置（PDF）")
    private String pathPdf;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "0:不删除 1：删除")
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private String configId;
}
