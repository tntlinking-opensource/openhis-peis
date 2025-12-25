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
 * 体检者上传状态(MdPeisState)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:08
 */
@Data
@TableName("md_peis_state")
@ApiModel(value = "MdPeisState", description = "体检者上传状态实体类")
public class MdPeisState extends Model<MdPeisState> implements Serializable {
    private static final long serialVersionUID = -64797373595181246L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "体检号")
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

    @ApiModelProperty(value = "是否是批量登记的，批量登记的不进排队。数据库中增加此列前的数据，isBatchRegistered都是0。")
    private Integer isBatchRegistered;

    @ApiModelProperty(value = "济南上传错误原因")
    private String jinanMsg;

    @ApiModelProperty(value = "济南上传状态")
    private Integer jinanStatus;

    @ApiModelProperty(value = "上传时间")
    private Date uploadDate;

    @ApiModelProperty(value = "职业病平台ID")
    private String healthCareId;
}
