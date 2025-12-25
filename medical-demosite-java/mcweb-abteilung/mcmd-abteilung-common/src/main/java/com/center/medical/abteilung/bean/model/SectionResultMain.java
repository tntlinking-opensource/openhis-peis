package com.center.medical.abteilung.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * KS科室检查结果主表(SectionResultMain)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:18
 */
@Data
@TableName("md_section_result_main")
@ApiModel(value = "SectionResultMain", description = "KS科室检查结果主表实体类")
public class SectionResultMain extends Model<SectionResultMain> implements Serializable {
    private static final long serialVersionUID = 858511106615940146L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
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

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "分检是否已审核 0和null.未审核 1.已审核 ")
    private Integer isAudit;

    @ApiModelProperty(value = "审核人ID")
    private String auditId;

    @ApiModelProperty(value = "审核时间")
    private Date auditTime;

    @ApiModelProperty(value = "标注体检者在本科室体检是否有危急值")
    private Integer isDanager;

    @ApiModelProperty(value = "危急值级别")
    private Integer danagerLevel;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "健康小结")
    private String conclusions;

    @ApiModelProperty(value = "是否删除")
    private Integer isDelete;

    @ApiModelProperty(value = "审核人姓名")
    private String auditName;

    @ApiModelProperty(value = "检查人姓名(rummager)")
    private String rummagerName;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "分检完成")
    private Integer isFinish;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "职业小结")
    private String zyConclusions;

    @ApiModelProperty(value = "关联表名")
    private String associativeTable;

    @ApiModelProperty(value = "短号体检号")
    private Integer shortCode;

    @ApiModelProperty(value = "图片类型")
    private String fileType;

    @ApiModelProperty(value = "图片路径")
    private String filePath;

    @ApiModelProperty(value = "pacs_conclusions")
    private String pacsConclusions;

    @ApiModelProperty(value = "zy_pacs_conclusions")
    private String zyPacsConclusions;

    @TableField(exist = false)
    @ApiModelProperty(value = "科室名称")
    private String deptName;

    @TableField(exist = false)
    @ApiModelProperty(value = "录入人姓名")
    private String writeName;
}
