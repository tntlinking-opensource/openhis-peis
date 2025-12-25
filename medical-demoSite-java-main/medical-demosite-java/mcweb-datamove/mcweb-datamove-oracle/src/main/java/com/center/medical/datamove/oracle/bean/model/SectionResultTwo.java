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
 * KS科室检查结果表————结论词、小结(SectionResultTwo)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:45
 */
@Data
@TableName("SECTION_RESULT_TWO")
@ApiModel(value = "SectionResultTwo", description = "KS科室检查结果表————结论词、小结实体类")
public class SectionResultTwo extends Model<SectionResultTwo> implements Serializable {
    private static final long serialVersionUID = -91168042516835602L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
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
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "重症级别")
    private Integer intensiveLevel;

    @ApiModelProperty(value = "是否弃检")
    private Integer isUnchecked;

    @ApiModelProperty(value = "职业/健康")
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
    private String shortCode;
}
