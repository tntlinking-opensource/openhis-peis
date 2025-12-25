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
 * JC婚姻表(Dictmarriage)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:11
 */
@Data
@TableName("DICTMARRIAGE")
@ApiModel(value = "Dictmarriage", description = "JC婚姻表实体类")
public class Dictmarriage extends Model<Dictmarriage> implements Serializable {
    private static final long serialVersionUID = 221343644878678357L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "KEYMARRIAGE")
    private String keymarriage;

    @ApiModelProperty(value = "婚姻名称")
    private String marriageName;

    @ApiModelProperty(value = "婚姻代码")
    private String marriageCode;

    @ApiModelProperty(value = "MARRIAGE_CODE2")
    private String marriageCode2;

    @ApiModelProperty(value = "MARRIAGE_CODE3")
    private String marriageCode3;

    @ApiModelProperty(value = "MARRIAGE_CODEHM")
    private String marriageCodehm;

    @ApiModelProperty(value = "导出代码")
    private String marriageCodex;

    @ApiModelProperty(value = "视同已婚")
    private Integer fHasmarried;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "状态")
    private Double status;
}
