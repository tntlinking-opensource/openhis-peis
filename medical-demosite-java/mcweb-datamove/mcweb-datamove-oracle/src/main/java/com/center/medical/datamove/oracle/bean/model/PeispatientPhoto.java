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
 * 体检者头像(PeispatientPhoto)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:22
 */
@Data
@TableName("PEISPATIENT_PHOTO")
@ApiModel(value = "PeispatientPhoto", description = "体检者头像实体类")
public class PeispatientPhoto extends Model<PeispatientPhoto> implements Serializable {
    private static final long serialVersionUID = -47741726708123279L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "照片base64")
    private String picture;
}
