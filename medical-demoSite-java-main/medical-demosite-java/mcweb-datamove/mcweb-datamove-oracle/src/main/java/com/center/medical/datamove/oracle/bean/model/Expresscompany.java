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
 * 快递公司表(Expresscompany)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:39
 */
@Data
@TableName("EXPRESSCOMPANY")
@ApiModel(value = "Expresscompany", description = "快递公司表实体类")
public class Expresscompany extends Model<Expresscompany> implements Serializable {
    private static final long serialVersionUID = 955503261442269833L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "通知方式")
    private String expressName;

    @ApiModelProperty(value = "联系方式")
    private String expressPhone;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}
