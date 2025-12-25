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
 * (Saleer)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:29
 */
@Data
@TableName("SALEER")
@ApiModel(value = "Saleer", description = "$tableInfo.comment实体类")
public class Saleer extends Model<Saleer> implements Serializable {
    private static final long serialVersionUID = -44096635458980472L;

    @ApiModelProperty(value = "${column.comment}")
    private String accountName;

    @ApiModelProperty(value = "${column.comment}")
    private String accountNo;

    @ApiModelProperty(value = "${column.comment}")
    private String useStatusId;

    @ApiModelProperty(value = "${column.comment}")
    private Date ctDate;

    @ApiModelProperty(value = "${column.comment}")
    private Date ltDate;

    @ApiModelProperty(value = "${column.comment}")
    private String centerorgname;

    @ApiModelProperty(value = "${column.comment}")
    private String md5;
}
