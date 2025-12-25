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
 * 体检者收费项目(MdBkPatientfeeitem)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:13
 */
@Data
@TableName("md_bk_patientfeeitem")
@ApiModel(value = "MdBkPatientfeeitem", description = "体检者收费项目实体类")
public class MdBkPatientfeeitem extends Model<MdBkPatientfeeitem> implements Serializable {
    private static final long serialVersionUID = -11176226787603269L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "体检者id")
    private String idPatient;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "收费检查项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "实付价格")
    private Double factprice;
}
