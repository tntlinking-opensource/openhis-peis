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
 * 核酸检测报告上传记录(MdNuclein)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:28
 */
@Data
@TableName("md_nuclein")
@ApiModel(value = "MdNuclein", description = "核酸检测报告上传记录实体类")
public class MdNuclein extends Model<MdNuclein> implements Serializable {
    private static final long serialVersionUID = -29100893410119267L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "上传人")
    private String creater;
}
