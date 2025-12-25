package com.center.medical.datamove.common.bean.model;


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
 * @since 2023-07-17 20:54:39
 */
@Data
@TableName("sys_upload_type")
@ApiModel(value = "SysUploadType", description = "数据上传接收日志数据类型实体类")
public class SysUploadType extends Model<SysUploadType> implements Serializable {
    private static final long serialVersionUID = -29646076286294549L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "类型代码")
    private Integer uploadType;

    @ApiModelProperty(value = "类型名称")
    private String uploadName;

    @ApiModelProperty(value = "表名")
    private String tableName;
}
