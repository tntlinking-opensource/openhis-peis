package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 对比报告表，用于存储对报告都有哪些生成过(CompareReport)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:56
 */
@Data
@TableName("md_compare_report")
@ApiModel(value = "CompareReport", description = "对比报告表，用于存储对报告都有哪些生成过实体类")
public class CompareReport extends Model<CompareReport> implements Serializable {
    private static final long serialVersionUID = 913357767333425457L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "config_id")
    private String configId;
}
