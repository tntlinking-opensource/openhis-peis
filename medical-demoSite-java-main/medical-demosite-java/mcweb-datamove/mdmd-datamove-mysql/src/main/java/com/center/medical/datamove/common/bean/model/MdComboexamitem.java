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
 * 用于判断职业小结(MdComboexamitem)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:17
 */
@Data
@TableName("md_comboexamitem")
@ApiModel(value = "MdComboexamitem", description = "用于判断职业小结实体类")
public class MdComboexamitem extends Model<MdComboexamitem> implements Serializable {
    private static final long serialVersionUID = 380705378160112732L;

    @ApiModelProperty(value = "危害因素Id")
    private String harmId;

    @ApiModelProperty(value = "检查项目Id")
    private String examId;

    @ApiModelProperty(value = "收费项目Id")
    private String itemId;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "男性上限")
    private Double scopeUpper;

    @ApiModelProperty(value = "男性下限")
    private Double scoperFloor;

    @ApiModelProperty(value = "女性上限")
    private Double gscopeupper;

    @ApiModelProperty(value = "女性下限")
    private Double gscoperfloor;

    @ApiModelProperty(value = "最小套餐ID")
    private String comboId;

    @ApiModelProperty(value = "职业体检类别，详见：com.world.center.bean.enums.MedicalType")
    private String medicalType;

    @ApiModelProperty(value = "ks_id")
    private String ksId;
}
