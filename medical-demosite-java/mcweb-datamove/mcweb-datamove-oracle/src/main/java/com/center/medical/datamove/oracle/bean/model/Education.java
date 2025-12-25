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
 * JC教育程度(Education)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:33
 */
@Data
@TableName("EDUCATION")
@ApiModel(value = "Education", description = "JC教育程度实体类")
public class Education extends Model<Education> implements Serializable {
    private static final long serialVersionUID = -70161298652030919L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "KEYEDUCATION")
    private String keyeducation;

    @ApiModelProperty(value = "教育名称")
    private String educationName;

    @ApiModelProperty(value = "教育编码")
    private String educationCode;

    @ApiModelProperty(value = "EDUCATION_CODE2")
    private String educationCode2;

    @ApiModelProperty(value = "EDUCATION_CODE3")
    private String educationCode3;

    @ApiModelProperty(value = "EDUCATION_CODEHM")
    private String educationCodehm;

    @ApiModelProperty(value = "教育导出代码")
    private String educationCodex;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
