package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * (Kingdeereserway)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:31
 */
@Data
@TableName("KINGDEERESERWAY")
@ApiModel(value = "Kingdeereserway", description = "$tableInfo.comment实体类")
public class Kingdeereserway extends Model<Kingdeereserway> implements Serializable {
    private static final long serialVersionUID = 463504767601629528L;

    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String accountNo;

    @ApiModelProperty(value = "${column.comment}")
    private String accountName;

    @ApiModelProperty(value = "${column.comment}")
    private String useStatusId;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;
}
