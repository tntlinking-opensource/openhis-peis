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
 * 职业问诊签名图片(PeispatientConsultationPic)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:19
 */
@Data
@TableName("PEISPATIENT_CONSULTATION_PIC")
@ApiModel(value = "PeispatientConsultationPic", description = "职业问诊签名图片实体类")
public class PeispatientConsultationPic extends Model<PeispatientConsultationPic> implements Serializable {
    private static final long serialVersionUID = -12981698508070521L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "签名base64")
    private String signPicture;

    @ApiModelProperty(value = "签名日期")
    private Date datecreated;
}
