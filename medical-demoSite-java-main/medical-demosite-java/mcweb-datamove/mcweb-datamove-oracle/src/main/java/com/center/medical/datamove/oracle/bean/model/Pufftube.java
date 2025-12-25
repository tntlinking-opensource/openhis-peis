package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * (Pufftube)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:41
 */
@Data
@TableName("PUFFTUBE")
@ApiModel(value = "Pufftube", description = "$tableInfo.comment实体类")
public class Pufftube extends Model<Pufftube> implements Serializable {
    private static final long serialVersionUID = 995294718914405380L;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private String trontimage;

    @ApiModelProperty(value = "${column.comment}")
    private String sideimage;

    @ApiModelProperty(value = "${column.comment}")
    private String result;

    @ApiModelProperty(value = "${column.comment}")
    private Date createtime;

    @ApiModelProperty(value = "${column.comment}")
    private String fzx;

    @ApiModelProperty(value = "${column.comment}")
    private String isUpdate;

    @ApiModelProperty(value = "${column.comment}")
    private String md5;
}
