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
 * PACS-基础检查项(MdPacsBasexamltem)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:32
 */
@Data
@TableName("md_pacs_basexamltem")
@ApiModel(value = "MdPacsBasexamltem", description = "PACS-基础检查项实体类")
public class MdPacsBasexamltem extends Model<MdPacsBasexamltem> implements Serializable {
    private static final long serialVersionUID = 680658868001100328L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "检查项目名称")
    private String examitemName;

    @ApiModelProperty(value = "检查项目名称缩写")
    private String examitemNameabr;

    @ApiModelProperty(value = "检查项目打印名称")
    private String examitemNameprn;

    @ApiModelProperty(value = "检查项目英文名称")
    private String examitemNameeng;

    @ApiModelProperty(value = "检查项目英文名称缩写")
    private String examitemNameengabr;

    @ApiModelProperty(value = "用于男性")
    private Integer fMale;

    @ApiModelProperty(value = "用于女性")
    private Integer fFemale;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "0:健康检查类型1:职业检查类型2:健康+职业(职业)")
    private Integer type;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "0:未删除 1：删除")
    private Integer isDelete;
}
