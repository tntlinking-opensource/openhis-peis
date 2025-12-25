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
 * JC个人防护用品(MdZyFhclGr)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:27
 */
@Data
@TableName("md_zy_fhcl_gr")
@ApiModel(value = "MdZyFhclGr", description = "JC个人防护用品实体类")
public class MdZyFhclGr extends Model<MdZyFhclGr> implements Serializable {
    private static final long serialVersionUID = 108366127994354908L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;
}
