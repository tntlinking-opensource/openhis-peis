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
 * (FileChangeRecord)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:43
 */
@Data
@TableName("FILE_CHANGE_RECORD")
@ApiModel(value = "FileChangeRecord", description = "$tableInfo.comment实体类")
public class FileChangeRecord extends Model<FileChangeRecord> implements Serializable {
    private static final long serialVersionUID = 415698146554970168L;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Integer status;

    @ApiModelProperty(value = "${column.comment}")
    private String dir;
}
