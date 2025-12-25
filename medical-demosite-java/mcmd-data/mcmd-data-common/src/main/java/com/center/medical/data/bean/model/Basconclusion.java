package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 总检结论词(Basconclusion)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:54
 */
@Data
@TableName("md_basconclusion")
@ApiModel(value = "Basconclusion", description = "总检结论词实体类")
public class Basconclusion extends Model<Basconclusion> implements Serializable {
    private static final long serialVersionUID = -27584203699260491L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "维护标记")
    private String sysmanmark;

    @ApiModelProperty(value = "健值")
    private String keyconclusion;

    @ApiModelProperty(value = "结论名称")
    private String name;

    @ApiModelProperty(value = "${column.comment}")
    private String conclusionNameeng;

    @ApiModelProperty(value = "${column.comment}")
    private String conclusionCode;

    @ApiModelProperty(value = "${column.comment}")
    private String conclusionCode2;

    @ApiModelProperty(value = "${column.comment}")
    private String conclusionCode3;

    @ApiModelProperty(value = "${column.comment}")
    private String conclusionCodehm;

    @ApiModelProperty(value = "${column.comment}")
    private String conclusioncodex;

    @ApiModelProperty(value = "${column.comment}")
    private String idConclusiontype;

    @ApiModelProperty(value = "${column.comment}")
    private String idConclusiontype2;

    @ApiModelProperty(value = "${column.comment}")
    private String idConclusiontype3;

    @ApiModelProperty(value = "${column.comment}")
    private String idConclusiongroup;

    @ApiModelProperty(value = "${column.comment}")
    private String idConclusionlink;

    @ApiModelProperty(value = "${column.comment}")
    private String idConclusionmedinsurance;

    @ApiModelProperty(value = "${column.comment}")
    private String idConclusioncadre;

    @ApiModelProperty(value = "${column.comment}")
    private String idConclusionoccup;

    @ApiModelProperty(value = "${column.comment}")
    private String idSection;

    @ApiModelProperty(value = "疾病解释")
    private String depiction;

    @ApiModelProperty(value = "总检建议")
    private String suggestion;

    @ApiModelProperty(value = "健康建议")
    private String advice;

    @ApiModelProperty(value = "饮食指导")
    private String dietguide;

    @ApiModelProperty(value = "运动指导")
    private String sportguide;

    @ApiModelProperty(value = "健康知识")
    private String knowledge;

    @ApiModelProperty(value = "团体建议")
    private String suggestiongroup;

    @ApiModelProperty(value = "${column.comment}")
    private String idIcd10;

    @ApiModelProperty(value = "拼音输入码")
    private String inputCode;

    @ApiModelProperty(value = "${column.comment}")
    private String inputcodeb;

    @ApiModelProperty(value = "${column.comment}")
    private String inputcodec;

    public String getInputCode() {
        if (StringUtils.isEmpty(inputCode)) {
            return null;
        }
        return inputCode.toUpperCase();
    }

    @ApiModelProperty(value = "${column.comment}")
    private String inputcoded;

    @ApiModelProperty(value = "${column.comment}")
    private String inputcodee;

    @ApiModelProperty(value = "${column.comment}")
    private String disporder;

    @ApiModelProperty(value = "${column.comment}")
    private String idSpecialconclusionlist;

    @ApiModelProperty(value = "${column.comment}")
    private String fRealdisease;

    @ApiModelProperty(value = "男性病")
    private String fMaledisease;

    @ApiModelProperty(value = "女性病")
    private String fFemaledisease;

    @ApiModelProperty(value = "${column.comment}")
    private String fHideongroupreport;

    @ApiModelProperty(value = "${column.comment}")
    private String disporderOngroupreport;

    @ApiModelProperty(value = "${column.comment}")
    private String conclusionfindermatch;

    @ApiModelProperty(value = "CONCLUSIONMATCHEXCLUDESELF,conclusion")
    private String conclusion;

    @ApiModelProperty(value = "${column.comment}")
    private String fNormalconclusion;

    @ApiModelProperty(value = "${column.comment}")
    private String severedegree;

    @ApiModelProperty(value = "${column.comment}")
    private String fOccudiseaseobject;

    @ApiModelProperty(value = "${column.comment}")
    private String fOccudiseasecontraindication;

    @ApiModelProperty(value = "${column.comment}")
    private String suggestionoccudisease;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "是否进总检")
    private Integer isLong;

    @ApiModelProperty(value = "科室名称")
    private String depName;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "是否是公共的：1.是 0或NULL. 不是")
    private Integer isPublic;

    @ApiModelProperty(value = "分中心IDs，逗号拼接")
    private String fzxIds;

    @ApiModelProperty(value = "维护（创建）人")
    private String creater;

    @ApiModelProperty(value = "审核人")
    private String auditer;

    @ApiModelProperty(value = "审核时间")
    private Date auditTime;

    @ApiModelProperty(value = "审核状态（null不需要审核,不是总检插入）： 0.未审 1.已审")
    private Integer auditStatus;

    @ApiModelProperty(value = "上传状态（null不需要上传,不是总检插入）：0.未上传  1.已上传（总检插入的上传到线上）")
    private Integer scbz;

    @ApiModelProperty(value = "是否常用：0.不常用 1.常用")
    private Integer isInCommonUse;

    @TableField(exist = false)
    @ApiModelProperty(value = "相当于id")
    private String conclusionId;


    @TableField(exist = false)
    @ApiModelProperty(value = "状态")
    private String state;

    @TableField(exist = false)
    @ApiModelProperty(value = "表格名称")
    private String conName;

    @TableField(exist = false)
    @ApiModelProperty(value = "分中心")
    private String fzx;

    @TableField(exist = false)
    @ApiModelProperty(value = "部门名称")
    private String deptName;
}
