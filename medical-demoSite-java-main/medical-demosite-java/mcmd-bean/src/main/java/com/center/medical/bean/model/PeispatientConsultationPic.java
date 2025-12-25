package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 职业问诊签名图片(PeispatientConsultationPic)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:09
 */
@Data
@TableName("md_peispatient_consultation_pic")
@ApiModel(value = "PeispatientConsultationPic", description = "职业问诊签名图片实体类")
public class PeispatientConsultationPic extends Model<PeispatientConsultationPic> implements Serializable {
    private static final long serialVersionUID = 799453530990729948L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "签名base64")
    private String signPicture;

    @ApiModelProperty(value = "签名日期")
    private Date datecreated;


    public PeispatientConsultationPic(String patientcode, String signPicture, Date datecreated) {
        this.patientcode = patientcode;
        this.signPicture = signPicture;
        this.datecreated = datecreated;
    }


    public PeispatientConsultationPic() {
    }
}
