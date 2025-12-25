package com.center.medical.abteilung.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * KS科室检查结果表-结论词、小结(SectionResultTwo)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:19
 */
@Data
@TableName("md_section_result_two")
@ApiModel(value = "SectionResultTwo", description = "KS科室检查结果表-结论词、小结实体类")
public class SectionResultTwo extends Model<SectionResultTwo> implements Serializable {
    private static final long serialVersionUID = 284298341170408929L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "科室检查结果主表ID")
    private String mainId;

    @ApiModelProperty(value = "在搜索结论词与小结时必须先以本ID查询到所有的相关结论词，然后再按照检查项目体证词关联表ID找到所对应的体证词（小结）与结论词ID")
    private String verdictId;

    @ApiModelProperty(value = "检查项目体证词关联表ID")
    private String nodule;

    @ApiModelProperty(value = "是否阳性结果")
    private Integer posistive;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "重症级别")
    private Integer intensiveLevel;

    @ApiModelProperty(value = "是否弃检")
    private Integer isUnchecked;

    @ApiModelProperty(value = "0.健康 1.职业")
    private Integer diseaseHealth;

    @ApiModelProperty(value = "结论词ID")
    private String basconclusionId;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "是否删除")
    private Integer isDelete;

    @ApiModelProperty(value = "收费项目ID")
    private String chargesId;

    @ApiModelProperty(value = "检查描述")
    private String ms;

    @ApiModelProperty(value = "是否危急值")
    private Integer isDanger;

    @ApiModelProperty(value = "自由输入结果")
    private String inputResult;

    @ApiModelProperty(value = "体检类型 1.职业 0.非职业")
    private Integer tjlx;

    @ApiModelProperty(value = "短号体检号")
    private Integer shortCode;



    public SectionResultTwo(String verdictId, String nodule,
                                Integer posistive, String patientcode, String basconclusion_id,
                                String division_id, String chargesId, Integer shortCode,
                                String feeitemId,Integer tjlx) {
        super();
        this.verdictId = verdictId;
        this.nodule = nodule;
        this.posistive = posistive;
        this.patientcode = patientcode;
        this.basconclusionId = basconclusion_id;
        this.divisionId = division_id;
        this.chargesId = chargesId;
        this.shortCode = shortCode;
        this.chargesId = feeitemId;
        this.tjlx=tjlx;
    }


    public SectionResultTwo() {
    }
}
