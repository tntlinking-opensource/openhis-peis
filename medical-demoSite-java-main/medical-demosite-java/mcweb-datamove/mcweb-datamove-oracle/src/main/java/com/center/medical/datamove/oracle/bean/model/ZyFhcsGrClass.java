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
 * JC个人防护用品种类(ZyFhcsGrClass)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:30:57
 */
@Data
@TableName("ZY_FHCS_GR_CLASS")
@ApiModel(value = "ZyFhcsGrClass", description = "JC个人防护用品种类实体类")
public class ZyFhcsGrClass extends Model<ZyFhcsGrClass> implements Serializable {
    private static final long serialVersionUID = -62672329912204459L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID号")
    private String id;

    @ApiModelProperty(value = "防护用品种类代码")
    private String defendIndividualClassCode;

    @ApiModelProperty(value = "防护用品种类")
    private String defendIndividualClass;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "操作员")
    private String dbUser;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;

    @ApiModelProperty(value = "假删标识")
    private Double isDelete;
}
