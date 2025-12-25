package com.center.medical.pacs.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * PACS-基础检查项实体类
 * @author xhp
 * @since 2023-03-29 7:56
 */
@Data
@TableName("md_pacs_basexamltem")
@ApiModel(value = "PacsBasexamltem", description = "PACS-基础检查项实体类")
public class PacsBasexamltem extends Model<PacsBasexamltem> implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "检查项目名称")
    private String examitemName;

//    @ApiModelProperty(value = "检查项目名称缩写")
//    private String examitemNameabr;
//
//    @ApiModelProperty(value = "打印名称")
//    private String examitemNameprn;
//
//    @ApiModelProperty(value = "检查项目英文名称")
//    private String examitemNameeng;
//
//    @ApiModelProperty(value = "检查项目名称英文缩写")
//    private String examitemNameengabr;

    @ApiModelProperty(value = "性别：0.代表男 1.代表女 2.通用")
    private Integer fMale;

//    @ApiModelProperty(value = "用于女性：0.代表男 1.代表女 2.通用")
//    private Integer fFemale;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "类型：0.健康检查类型 1.职业检查类型 2.健康+职业")
    private Integer type;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

}
