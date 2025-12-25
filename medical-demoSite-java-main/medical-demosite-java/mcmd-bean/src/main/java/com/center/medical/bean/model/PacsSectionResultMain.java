package com.center.medical.bean.model;

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
 * PACS-检查结果主表(PacsSectionResultMain)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:09
 */
@Data
@TableName("md_pacs_section_result_main")
@ApiModel(value = "PacsSectionResultMain", description = "PACS-检查结果主表实体类")
public class PacsSectionResultMain extends Model<PacsSectionResultMain> implements Serializable {
    private static final long serialVersionUID = -90917668143682902L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "检查人ID")
    private String rummagerId;

    @ApiModelProperty(value = "录入人ID")
    private String writeId;

    @ApiModelProperty(value = "检查时间")
    private Date rummagerTime;

    @ApiModelProperty(value = "录入时间")
    private Date writeTime;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "分检是否已审核")
    private Integer isAudit;

    @ApiModelProperty(value = "审核人ID")
    private String auditId;

    @ApiModelProperty(value = "审核时间")
    private Date auditTime;

    @ApiModelProperty(value = "是否危急值")
    private Integer isDanager;

    @ApiModelProperty(value = "危急值级别")
    private Integer danagerLevel;

    @ApiModelProperty(value = "健康小结")
    private String conclusions;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "审核人姓名")
    private String auditName;

    @ApiModelProperty(value = "检查人姓名")
    private String rummagerName;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "上传标识   null未上传  1已上传  分检是否已完成(不用)")
    private Integer isFinish;

    @ApiModelProperty(value = "职业小结")
    private String zyConclusions;

    @ApiModelProperty(value = "相关表名")
    private String associativeTable;

    @ApiModelProperty(value = "短体检号")
    private Integer shortCode;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "文件路径")
    private String filePath;

    @ApiModelProperty(value = "描述")
    private String description;
}
