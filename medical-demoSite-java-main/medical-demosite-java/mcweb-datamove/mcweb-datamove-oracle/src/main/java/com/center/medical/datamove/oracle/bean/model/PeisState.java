package com.center.medical.datamove.oracle.bean.model;


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
 * 体检者上传状态(PeisState)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:03
 */
@Data
@TableName("PEIS_STATE")
@ApiModel(value = "PeisState", description = "体检者上传状态实体类")
public class PeisState extends Model<PeisState> implements Serializable {
    private static final long serialVersionUID = 408293941944278235L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "是否从标软提取")
    private Integer fDiffperson;

    @ApiModelProperty(value = "DESCRIBE上传标志")
    private Integer fInneroper;

    @ApiModelProperty(value = "区疾控上传标志")
    private String patientflag;

    @ApiModelProperty(value = "收费信息上传标志")
    private Integer idPatientclass2;

    @ApiModelProperty(value = "收费项目上传标志")
    private Integer idGuidancereturnedby;

    @ApiModelProperty(value = "体检者上传标志")
    private Integer scbs;

    @ApiModelProperty(value = "核酸报告上传标志")
    private Integer countreportpdf;

    @ApiModelProperty(value = "是否上传至大数据(线上)")
    private Integer countreportcomparexml;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isBatchRegistered;

    @ApiModelProperty(value = "${column.comment}")
    private String jinanMsg;

    @ApiModelProperty(value = "${column.comment}")
    private Integer jinanStatus;

    @ApiModelProperty(value = "上传时间")
    private Date uploadDate;

    @ApiModelProperty(value = "职业病平台ID")
    private String healthCareId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDongruanRecieved;
}
