package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检者头像(PeispatientPhoto)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:09
 */
@Data
@TableName("md_peispatient_photo")
@ApiModel(value = "PeispatientPhoto", description = "体检者头像实体类")
public class PeispatientPhoto extends Model<PeispatientPhoto> implements Serializable {
    private static final long serialVersionUID = 611620558338459438L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "照片base64")
    private String picture;


    public PeispatientPhoto() {
    }

    public PeispatientPhoto(String patientcode, String picture) {
        this.patientcode = patientcode;
        this.picture = picture;
    }
}
