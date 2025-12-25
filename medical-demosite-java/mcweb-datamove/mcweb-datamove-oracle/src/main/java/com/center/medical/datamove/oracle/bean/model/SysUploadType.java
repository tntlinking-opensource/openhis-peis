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
 * 数据上传接收日志数据类型(SysUploadType)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:09
 */
@Data
@TableName("SYS_UPLOAD_TYPE")
@ApiModel(value = "SysUploadType", description = "数据上传接收日志数据类型实体类")
public class SysUploadType extends Model<SysUploadType> implements Serializable {
    private static final long serialVersionUID = -80393010203450197L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "类型代码")
    private String uploadType;

    @ApiModelProperty(value = "类型名称")
    private String uploadName;
}
