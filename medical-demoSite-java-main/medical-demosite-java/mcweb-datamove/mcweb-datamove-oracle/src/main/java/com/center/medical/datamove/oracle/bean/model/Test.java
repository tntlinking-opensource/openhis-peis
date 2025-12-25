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
 * (Test)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:22
 */
@Data
@TableName("test")
@ApiModel(value = "Test", description = "$tableInfo.comment实体类")
public class Test extends Model<Test> implements Serializable {
    private static final long serialVersionUID = -65086690013436406L;

    @ApiModelProperty(value = "${column.comment}")
    private String a;
}
