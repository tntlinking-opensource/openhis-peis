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
 * KS图片存储表(Pricture)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:37
 */
@Data
@TableName("PRICTURE")
@ApiModel(value = "Pricture", description = "KS图片存储表实体类")
public class Pricture extends Model<Pricture> implements Serializable {
    private static final long serialVersionUID = -32213646717680347L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "路径")
    private String url;

    @ApiModelProperty(value = "科室ID")
    private String sectionId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "图片名称")
    private String prictureName;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
