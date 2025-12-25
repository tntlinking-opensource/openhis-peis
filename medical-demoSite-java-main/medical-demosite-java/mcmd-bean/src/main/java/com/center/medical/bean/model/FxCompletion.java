package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 本次职业健康检查漏检人员及漏检项目人员一览表(FxCompletion)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:59
 */
@Data
@TableName("md_fx_completion")
@ApiModel(value = "FxCompletion", description = "本次职业健康检查漏检人员及漏检项目人员一览表实体类")
public class FxCompletion extends Model<FxCompletion> implements Serializable {
    private static final long serialVersionUID = 138901543120516311L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @ApiModelProperty(value = "样本ID")
    private String sampleId;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "接害工龄")
    private Integer jhgl;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "接害因素")
    private String jhysIds;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @ApiModelProperty(value = "婚姻")
    private String marriage;

    @ApiModelProperty(value = "婚姻ID")
    private String idMarriage;

    @ApiModelProperty(value = "未检项目（名称）")
    private String uncheckedItems;

    @ApiModelProperty(value = "未检项目（ID）")
    private String uncheckedItemids;

    @ApiModelProperty(value = "体检时间")
    private Date medicaldate;

    @ApiModelProperty(value = "已开始体检：0或null.否 1.是")
    private Integer fExamstarted;

    @ApiModelProperty(value = "分检完成：0或null.否 1.是")
    private Integer fReadytofinal;

    @ApiModelProperty(value = "职业体检状态，详见：ExamStatus")
    private Integer zytjzt;

    @ApiModelProperty(value = "工种")
    private String trades;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "报告是否已打印：0.未打印 1.已打印")
    private Integer isPrint;

    @ApiModelProperty(value = "健康体检状态")
    private Integer jktjzt;

    @ApiModelProperty(value = "已登记：0.未登记 1.已登记")
    private Integer registered;

    @ApiModelProperty(value = "是否算作总人数里（复查和补检的不算），1是0否")
    private Integer fRegistered;

    @ApiModelProperty(value = "职业总检时间")
    private Date dateregisternotime;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;
}
