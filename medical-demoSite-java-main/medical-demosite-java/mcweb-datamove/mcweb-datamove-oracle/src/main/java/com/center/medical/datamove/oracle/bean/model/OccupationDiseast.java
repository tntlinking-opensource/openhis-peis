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
 * JC职业病名称(OccupationDiseast)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:16
 */
@Data
@TableName("OCCUPATION_DISEAST")
@ApiModel(value = "OccupationDiseast", description = "JC职业病名称实体类")
public class OccupationDiseast extends Model<OccupationDiseast> implements Serializable {
    private static final long serialVersionUID = 982270025938026864L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    private String id;

    @ApiModelProperty(value = "职业病编码")
    private String occupationDiseastCode;

    @ApiModelProperty(value = "职业病名称")
    private String occupationDiseast;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "操作员")
    private String dbUser;

    @ApiModelProperty(value = "职业病分类ID")
    private String occupationDiseastClass;

    @ApiModelProperty(value = "createdate")
    private Date createdate;

    @ApiModelProperty(value = "modifydate")
    private Date modifydate;

    @ApiModelProperty(value = "假删")
    private Double isDelete;
}
