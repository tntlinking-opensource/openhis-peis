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
 * JC危害因素取值范围(MdWhysqzfw)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:49:32
 */
@Data
@TableName("md_whysqzfw")
@ApiModel(value = "MdWhysqzfw", description = "JC危害因素取值范围实体类")
public class MdWhysqzfw extends Model<MdWhysqzfw> implements Serializable {
    private static final long serialVersionUID = -66814812038076446L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "危害因素名字")
    private String harmName;

    @ApiModelProperty(value = "取值范围单位")
    private String unit;

    @ApiModelProperty(value = "检查项目名称")
    private String jcName;

    @ApiModelProperty(value = "检查项目ID")
    private String jcId;

    @ApiModelProperty(value = "危害因素ID")
    private String whId;

    @ApiModelProperty(value = "男取值范围上限")
    private Double scopeUpper;

    @ApiModelProperty(value = "男取值范围下限")
    private Double scoperFloor;

    @ApiModelProperty(value = "范围名称")
    private String scoperName;

    @ApiModelProperty(value = "范围代码")
    private String scoperCode;

    @ApiModelProperty(value = "LIS编号")
    private String lisId;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "职业/综合")
    private Integer zyorjk;

    @ApiModelProperty(value = "危害因素输入码")
    private String inputCode;

    @ApiModelProperty(value = "男女标识")
    private Integer sex;

    @ApiModelProperty(value = "女取值范围上限")
    private Double gscopeupper;

    @ApiModelProperty(value = "女取值范围下限")
    private Double gscoperfloor;
}
