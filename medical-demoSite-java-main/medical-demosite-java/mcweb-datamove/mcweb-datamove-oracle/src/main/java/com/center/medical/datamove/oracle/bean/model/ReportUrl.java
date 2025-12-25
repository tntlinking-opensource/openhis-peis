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
 * BG科室报告目录表(ReportUrl)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:17
 */
@Data
@TableName("REPORT_URL")
@ApiModel(value = "ReportUrl", description = "BG科室报告目录表实体类")
public class ReportUrl extends Model<ReportUrl> implements Serializable {
    private static final long serialVersionUID = 855321100668849719L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "WORD存放位置")
    private String wordUrl;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否为头文件")
    private Integer isHead;

    @ApiModelProperty(value = "PDF存放位置")
    private String pdfUrl;

    @ApiModelProperty(value = "科室名称")
    private String depName;

    @ApiModelProperty(value = "职业/健康  0：健康；1 职业")
    private Integer diseaseHealth;

    @ApiModelProperty(value = "pdf存放位置（用于单科室出报告存放pdf地址）")
    private String pdfUrlHead;

    @ApiModelProperty(value = "word存放位置（用于单科室出报告存放word地址）")
    private String wordUrlHead;

    @ApiModelProperty(value = "短号体检号")
    private String shortCode;

    @ApiModelProperty(value = "${column.comment}")
    private String configId;

    @ApiModelProperty(value = "${column.comment}")
    private String creator;
}
