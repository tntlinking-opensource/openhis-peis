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
 * Pacs数据(PacsResult)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:51
 */
@Data
@TableName("PACS_RESULT")
@ApiModel(value = "PacsResult", description = "Pacs数据实体类")
public class PacsResult extends Model<PacsResult> implements Serializable {
    private static final long serialVersionUID = -95730861863352186L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
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

    @ApiModelProperty(value = "检查师")
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

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "文件路径")
    private String imagefullpath;

    @ApiModelProperty(value = "检查人用户名")
    private String username;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "收费项目ID")
    private String itemId;

    @ApiModelProperty(value = "收费项目名称")
    private String itemName;

    @ApiModelProperty(value = "短号体检号")
    private String shortCode;

    @ApiModelProperty(value = "${column.comment}")
    private Date writeDate;

    @ApiModelProperty(value = "${column.comment}")
    private String pacsItemId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isNewPacs;

    @ApiModelProperty(value = "${column.comment}")
    private String updateNum;

    @ApiModelProperty(value = "${column.comment}")
    private String auditDoctor;
}
