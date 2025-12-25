package com.center.medical.olddata.bean.model;


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
 * 职业问诊(PeispatientConsultation)表实体类
 *
 * @author ay
 * @since 2024-06-05 14:59:12
 */
@Data
@TableName("PEISPATIENT_CONSULTATION")
@ApiModel(value = "PeispatientConsultation", description = "职业问诊实体类")
public class OrPeispatientConsultation extends Model<OrPeispatientConsultation> implements Serializable {
    private static final long serialVersionUID = -58321003097343792L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;


    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;


    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;


    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;


    @ApiModelProperty(value = "${column.comment}")
    private String everOfDisease;


    @ApiModelProperty(value = "${column.comment}")
    private String ccnl;


    @ApiModelProperty(value = "${column.comment}")
    private String jq;


    @ApiModelProperty(value = "${column.comment}")
    private String zq;


    @ApiModelProperty(value = "${column.comment}")
    private String tjnl;


    @ApiModelProperty(value = "${column.comment}")
    private String familyNumber;


    @ApiModelProperty(value = "${column.comment}")
    private String zc;


    @ApiModelProperty(value = "${column.comment}")
    private String sc;


    @ApiModelProperty(value = "${column.comment}")
    private String lc;


    @ApiModelProperty(value = "${column.comment}")
    private String jt;


    @ApiModelProperty(value = "${column.comment}")
    private String ywrc;


    @ApiModelProperty(value = "${column.comment}")
    private String abstainSmokeNote;


    @ApiModelProperty(value = "${column.comment}")
    private String everydaySmokeN;


    @ApiModelProperty(value = "${column.comment}")
    private String smokeYear;


    @ApiModelProperty(value = "${column.comment}")
    private String noKissTheCup;


    @ApiModelProperty(value = "${column.comment}")
    private String betweenKissTheCup;


    @ApiModelProperty(value = "${column.comment}")
    private String evermoreKiss;


    @ApiModelProperty(value = "${column.comment}")
    private String abstainLostKiss;


    @ApiModelProperty(value = "${column.comment}")
    private String kissYearN;


    @ApiModelProperty(value = "${column.comment}")
    private String kissAmount;


    @ApiModelProperty(value = "${column.comment}")
    private String kissType;


    @ApiModelProperty(value = "${column.comment}")
    private String familyOfDisease;


    @ApiModelProperty(value = "${column.comment}")
    private String symptom;


    @ApiModelProperty(value = "${column.comment}")
    private Integer isAudit;


    @ApiModelProperty(value = "${column.comment}")
    private String everOfDiseaseRemark;

}
