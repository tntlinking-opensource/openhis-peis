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
 * PACS-数据(MdPacsResult)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:35
 */
@Data
@TableName("md_pacs_result")
@ApiModel(value = "MdPacsResult", description = "PACS-数据实体类")
public class MdPacsResult extends Model<MdPacsResult> implements Serializable {
    private static final long serialVersionUID = -65964312441878671L;

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
    private Integer shortCode;

    @ApiModelProperty(value = "PACS系统：录入时间")
    private Date writeDate;

    @ApiModelProperty(value = "PCAS系统中的基础收费项目ID")
    private String pacsItemId;

    @ApiModelProperty(value = "是否是新PACS")
    private Integer isNewPacs;

    @ApiModelProperty(value = "版本号")
    private Integer updateNum;

    @ApiModelProperty(value = "审核者用户名")
    private String auditDoctor;
}
