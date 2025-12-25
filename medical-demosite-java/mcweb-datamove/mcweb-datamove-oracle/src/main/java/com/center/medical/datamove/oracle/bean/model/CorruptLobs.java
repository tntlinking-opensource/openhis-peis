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
 * (CorruptLobs)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:17:51
 */
@Data
@TableName("CORRUPT_LOBS")
@ApiModel(value = "CorruptLobs", description = "$tableInfo.comment实体类")
public class CorruptLobs extends Model<CorruptLobs> implements Serializable {
    private static final long serialVersionUID = 973664227825766530L;

    @ApiModelProperty(value = "${column.comment}")
    private String corruptRowid;
}
