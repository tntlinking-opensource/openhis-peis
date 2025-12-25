package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检者上传状态(PeisState)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:09
 */
@Data
@TableName("md_peis_state")
@ApiModel(value = "PeisState", description = "体检者上传状态实体类")
public class PeisState extends Model<PeisState> implements Serializable {
    private static final long serialVersionUID = -30836670097943421L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
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


    public PeisState() {
    }

    public PeisState(String patientcode) {
        this.patientcode = patientcode;
        this.fDiffperson=0;
        this.fInneroper=0;
        this.patientflag="0";
        this.idPatientclass2=0;
        this.idGuidancereturnedby=0;
        this.scbs=0;
        this.countreportpdf=0;
        this.countreportcomparexml=0;
        this.isBatchRegistered=0;
        this.jinanStatus=0;
    }
}
