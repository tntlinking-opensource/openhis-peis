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
 * (BkPatientfeeitem)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:50
 */
@Data
@TableName("BK_PATIENTFEEITEM")
@ApiModel(value = "BkPatientfeeitem", description = "$tableInfo.comment实体类")
public class BkPatientfeeitem extends Model<BkPatientfeeitem> implements Serializable {
    private static final long serialVersionUID = -45762263613066807L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String idPatient;

    @ApiModelProperty(value = "${column.comment}")
    private String note;

    @ApiModelProperty(value = "${column.comment}")
    private String examfeeitemName;

    @ApiModelProperty(value = "${column.comment}")
    private String factprice;
}
