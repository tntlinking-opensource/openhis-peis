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
 * (PacsPeispatientfeeitem)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:50
 */
@Data
@TableName("PACS_PEISPATIENTFEEITEM")
@ApiModel(value = "PacsPeispatientfeeitem", description = "$tableInfo.comment实体类")
public class PacsPeispatientfeeitem extends Model<PacsPeispatientfeeitem> implements Serializable {
    private static final long serialVersionUID = 557275776108704240L;

    @ApiModelProperty(value = "${column.comment}")
    private String idPatientfeeitem;

    @ApiModelProperty(value = "${column.comment}")
    private String idPatient;

    @ApiModelProperty(value = "${column.comment}")
    private String idExamfeeitem;

    @ApiModelProperty(value = "${column.comment}")
    private String examfeeitemName;

    @ApiModelProperty(value = "${column.comment}")
    private Double price;

    @ApiModelProperty(value = "${column.comment}")
    private Double factprice;

    @ApiModelProperty(value = "${column.comment}")
    private Double settleprice;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fAddeditem;

    @ApiModelProperty(value = "${column.comment}")
    private String idPayway;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fRegistered;

    @ApiModelProperty(value = "${column.comment}")
    private String idDoctorreg;

    @ApiModelProperty(value = "${column.comment}")
    private String doctorregR;

    @ApiModelProperty(value = "${column.comment}")
    private Date registertime;

    @ApiModelProperty(value = "${column.comment}")
    private String idPatientregistersheet;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fRegreturned;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fFeecharged;

    @ApiModelProperty(value = "${column.comment}")
    private String idFeecharger;

    @ApiModelProperty(value = "${column.comment}")
    private String feechargerNameR;

    @ApiModelProperty(value = "${column.comment}")
    private Date feechargetime;

    @ApiModelProperty(value = "${column.comment}")
    private String idFeediscounter;

    @ApiModelProperty(value = "${column.comment}")
    private Double batchnumber;

    @ApiModelProperty(value = "${column.comment}")
    private Double tubeposition;

    @ApiModelProperty(value = "${column.comment}")
    private Double samplenumber;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fLabsampled;

    @ApiModelProperty(value = "${column.comment}")
    private String idLabsampler;

    @ApiModelProperty(value = "${column.comment}")
    private String labsamplerNameR;

    @ApiModelProperty(value = "${column.comment}")
    private Date labsampletime;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fLabsendtolis;

    @ApiModelProperty(value = "${column.comment}")
    private Date labsendtolistime;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fLabrcvdfromlis;

    @ApiModelProperty(value = "${column.comment}")
    private Date labrcvdfromlistime;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fGiveup;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fExaminated;

    @ApiModelProperty(value = "${column.comment}")
    private String idPatientexamdepart;

    @ApiModelProperty(value = "${column.comment}")
    private String idExamdoctor;

    @ApiModelProperty(value = "${column.comment}")
    private String examdoctorNameR;

    @ApiModelProperty(value = "${column.comment}")
    private Date examinatetime;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fMarkFeereturn;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fWorkInnerModify;

    @ApiModelProperty(value = "${column.comment}")
    private Integer severedegree;

    @ApiModelProperty(value = "${column.comment}")
    private String positivesummary;

    @ApiModelProperty(value = "${column.comment}")
    private String interfacemarks;

    @ApiModelProperty(value = "${column.comment}")
    private String urlresult;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fDelayed;

    @ApiModelProperty(value = "${column.comment}")
    private Date dtDelayedtill;

    @ApiModelProperty(value = "${column.comment}")
    private String notewhydelayed;

    @ApiModelProperty(value = "${column.comment}")
    private String idExamplace;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fTransferedhl7;

    @ApiModelProperty(value = "${column.comment}")
    private String qty;

    @ApiModelProperty(value = "${column.comment}")
    private String feeitemdesc;

    @ApiModelProperty(value = "${column.comment}")
    private String feeitemsummary;

    @ApiModelProperty(value = "${column.comment}")
    private String idTypist;

    @ApiModelProperty(value = "${column.comment}")
    private String idExamapprovedby;

    @ApiModelProperty(value = "${column.comment}")
    private String examapprovedbyNameR;

    @ApiModelProperty(value = "${column.comment}")
    private String samplenumberfromlis;

    @ApiModelProperty(value = "${column.comment}")
    private String samplemsgfromlis;

    @ApiModelProperty(value = "${column.comment}")
    private String receiptsheetnofromhis;

    @ApiModelProperty(value = "${column.comment}")
    private String feeitemrequestno;

    @ApiModelProperty(value = "${column.comment}")
    private String samplestatus;

    @ApiModelProperty(value = "${column.comment}")
    private String backupsingleitemcopiesprinted;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fFeechargedinttrans;

    @ApiModelProperty(value = "${column.comment}")
    private String giveupnotelet;

    @ApiModelProperty(value = "${column.comment}")
    private Date createDate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifyDate;

    @ApiModelProperty(value = "${column.comment}")
    private Integer sfjx;

    @ApiModelProperty(value = "${column.comment}")
    private String jxys;

    @ApiModelProperty(value = "${column.comment}")
    private String idKs;

    @ApiModelProperty(value = "${column.comment}")
    private Integer count;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private Integer changeItem;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isMintc;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isbx;

    @ApiModelProperty(value = "${column.comment}")
    private String bxcount;

    @ApiModelProperty(value = "${column.comment}")
    private Double endtuiprice;

    @ApiModelProperty(value = "${column.comment}")
    private Double actualprice;

    @ApiModelProperty(value = "${column.comment}")
    private String shortCode;

    @ApiModelProperty(value = "${column.comment}")
    private Integer sfjj;
}
