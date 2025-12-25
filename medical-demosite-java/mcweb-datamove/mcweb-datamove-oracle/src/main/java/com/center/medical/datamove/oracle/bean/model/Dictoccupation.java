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
 * JC职业表(Dictoccupation)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:13
 */
@Data
@TableName("DICTOCCUPATION")
@ApiModel(value = "Dictoccupation", description = "JC职业表实体类")
public class Dictoccupation extends Model<Dictoccupation> implements Serializable {
    private static final long serialVersionUID = -55849531311637049L;

    @ApiModelProperty(value = "KEYOCCUPATION")
    private String keyOccupation;

    @ApiModelProperty(value = "职业名称")
    private String occupationName;

    @ApiModelProperty(value = "职业代码")
    private String occupationCode;

    @ApiModelProperty(value = "OCCUPATION_CODE2")
    private String occupationCode2;

    @ApiModelProperty(value = "OCCUPATION_CODE3")
    private String occupationCode3;

    @ApiModelProperty(value = "OCCUPATION_CODEHM")
    private String occupationCodehm;

    @ApiModelProperty(value = "导出代码")
    private String occupationcodex;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "假删状态")
    private Double isDelete;
}
