package com.center.medical.datamove.common.bean.model;


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
 * 职业问诊签名图片(MdPeispatientConsultationPic)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:12
 */
@Data
@TableName("md_peispatient_consultation_pic")
@ApiModel(value = "MdPeispatientConsultationPic", description = "职业问诊签名图片实体类")
public class MdPeispatientConsultationPic extends Model<MdPeispatientConsultationPic> implements Serializable {
    private static final long serialVersionUID = -64726174626912435L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "签名base64")
    private String signPicture;

    @ApiModelProperty(value = "签名日期")
    private Date datecreated;
}
