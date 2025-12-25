package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * PACS-数据(PacsResult)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:09
 */
@Data
@TableName("md_pacs_result")
@ApiModel(value = "PacsResult", description = "PACS-数据实体类")
public class PacsResult extends Model<PacsResult> implements Serializable {
    private static final long serialVersionUID = 623769831746317441L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检者姓名")
    private String patientname;

    @ApiModelProperty(value = "收费项目代码")
    private String examfeeitemCode;

    @ApiModelProperty(value = "检查时间")
    private Date examdatetime;

    @ApiModelProperty(value = "检查人")
    private String examdoctor;

    @ApiModelProperty(value = "检查结果描述")
    private String examresultdesc;

    @ApiModelProperty(value = "检查结果总结")
    private String examresultsummary;

    @ApiModelProperty(value = "检查结果是否正常")
    private String examresultisnormal;

    @ApiModelProperty(value = "TransfterTarget")
    private String transftertarget;

    @ApiModelProperty(value = "F_ResultTransfered")
    private Integer fResulttransfered;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "文件路径")
    private String imagefullpath;

    @ApiModelProperty(value = "录入人用户名")
    private String username;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "收费项目ID")
    private String itemId;

    @ApiModelProperty(value = "收费项目名称")
    private String itemName;

    @ApiModelProperty(value = "短号体检号")
    private Integer shortCode;

    @ApiModelProperty(value = "PACS系统：录入时间")
    private Date writeDate;

    @ApiModelProperty(value = "PACS系统中的基础收费项目ID")
    private String pacsItemId;

    @ApiModelProperty(value = "是否是新PACS(体检系统PACS)   1体检系统pacs 2易影")
    private Integer isNewPacs;

    @ApiModelProperty(value = "版本号")
    private Integer updateNum;

    @ApiModelProperty(value = "审核者用户名")
    private String auditDoctor;


    public PacsResult(String patientCode, String patientName, String examFeeItemCode, Date examDateTime, String examDoctor, String examResultDesc, String examResultSummary, String examResultIsNormal,
                          String imageFullPath, String transfterTarget, Integer f_ResultTransfered,String userName,String ksId,String itemName,String itemId,Date writeDate,String pacsItemId) {
        super();
        this.patientcode = patientCode;
        this.patientname = patientName;
        this.examfeeitemCode = examFeeItemCode;
        this.examdatetime = examDateTime;
        this.examdoctor = examDoctor;
        this.examresultdesc = examResultDesc;
        this.examresultsummary = examResultSummary;
        this.examresultisnormal = examResultIsNormal;
        this.imagefullpath = imageFullPath;
        this.transftertarget = transfterTarget;
        this.fResulttransfered = f_ResultTransfered;
        this.username=userName;
        this.depId=ksId;
        this.itemName=itemName;
        this.itemId=itemId;
        this.writeDate=writeDate;
        this.pacsItemId=pacsItemId;
    }


    public PacsResult() {
    }
}
