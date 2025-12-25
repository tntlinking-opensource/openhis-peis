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
 * JC个人防护用品(ZyFhclGr)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:30:54
 */
@Data
@TableName("ZY_FHCL_GR")
@ApiModel(value = "ZyFhclGr", description = "JC个人防护用品实体类")
public class ZyFhclGr extends Model<ZyFhclGr> implements Serializable {
    private static final long serialVersionUID = -82106390948867306L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID号")
    private String id;

    @ApiModelProperty(value = "防护用品代码")
    private String defendIndividualCode;

    @ApiModelProperty(value = "防护用品")
    private String defendIndividual;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "操作员")
    private String dbUser;

    @ApiModelProperty(value = "防护用品种类代码")
    private String defendIndividualClass;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;

    @ApiModelProperty(value = "个人防护用品种类ID")
    private String grId;

    @ApiModelProperty(value = "假删标识1")
    private Double isDelete;
}
