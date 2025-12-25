package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 综合分析-人员一览表(FxPersonnelview)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:59
 */
@Data
@TableName("md_fx_personnelview")
@ApiModel(value = "FxPersonnelview", description = "综合分析-人员一览表实体类")
public class FxPersonnelview extends Model<FxPersonnelview> implements Serializable {
    private static final long serialVersionUID = 468432562341794892L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "样本ID")
    private String sampleId;

    @ApiModelProperty(value = "检查结果")
    private String positives;

    @ApiModelProperty(value = "处理意见")
    private String summaryText;

    @ApiModelProperty(value = "结论（序列号）")
    private Integer summarySerialno;

    @ApiModelProperty(value = "结论(名称)")
    private String occupationSummary;

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

    @ApiModelProperty(value = "工种")
    private String trades;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    public FxPersonnelview(String patientcode, String sampleId, String positives, String summaryText, String occupationSummary, String patientname, String sex, Integer age, Integer jhgl, String jhys,
                           String jhysIds, String trades, Integer summarySerialno) {
        super();
        this.patientcode = patientcode;
        this.sampleId = sampleId;
        this.positives = positives;
        this.summaryText = summaryText;
        this.occupationSummary = occupationSummary;
        this.patientname = patientname;
        this.sex = sex;
        this.age = age;
        this.jhgl = jhgl;
        this.jhys = jhys;
        this.jhysIds = jhysIds;
        this.trades = trades;
        this.summarySerialno = summarySerialno;
    }

    public FxPersonnelview() {
    }
}
