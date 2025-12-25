package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 教育程度(Education)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:58
 */
@Data
@TableName("md_education")
@ApiModel(value = "Education", description = "教育程度实体类")
public class Education extends Model<Education> implements Serializable {
    private static final long serialVersionUID = -72527660199129057L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
