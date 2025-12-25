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
 * KS图片存储表(MdPricture)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:15
 */
@Data
@TableName("md_pricture")
@ApiModel(value = "MdPricture", description = "KS图片存储表实体类")
public class MdPricture extends Model<MdPricture> implements Serializable {
    private static final long serialVersionUID = 576118471432794431L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
