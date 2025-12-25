package com.center.medical.report.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * BG科室报告目录表(ReportUrl)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-23 17:11:14
 */
@Data
@TableName("md_report_url")
@ApiModel(value = "ReportUrl", description = "BG科室报告目录表实体类")
public class ReportUrl extends Model<ReportUrl> implements Serializable {
    private static final long serialVersionUID = 721346020555749183L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
    private Integer shortCode;

    @ApiModelProperty(value = "配置文件id")
    private String configId;

    @ApiModelProperty(value = "创建人")
    private String creator;
}
