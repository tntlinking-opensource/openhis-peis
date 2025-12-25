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
 * (BadRows)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:16
 */
@Data
@TableName("BAD_ROWS")
@ApiModel(value = "BadRows", description = "$tableInfo.comment实体类")
public class BadRows extends Model<BadRows> implements Serializable {
    private static final long serialVersionUID = 506340593212862832L;

    @ApiModelProperty(value = "${column.comment}")
    private String rowId;

    @ApiModelProperty(value = "${column.comment}")
    private String oracleErrorCode;
}
