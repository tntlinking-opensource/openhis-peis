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
 * 数据上传接收日志(SysUploadLog)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:07
 */
@Data
@TableName("SYS_UPLOAD_LOG")
@ApiModel(value = "SysUploadLog", description = "数据上传接收日志实体类")
public class SysUploadLog extends Model<SysUploadLog> implements Serializable {
    private static final long serialVersionUID = 617634926830669754L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "分中心id")
    private String cid;

    @ApiModelProperty(value = "上传数据类型(SYS_UPLOAD_TYPE)代码")
    private String uploadType;

    @ApiModelProperty(value = "上传数据条数")
    private String uploadNum;

    @ApiModelProperty(value = "上传时间")
    private Date uploadDate;

    @ApiModelProperty(value = "上传结果")
    private Integer uploadStatus;

    @ApiModelProperty(value = "错误信息")
    private String msg;

    @ApiModelProperty(value = "ip地址")
    private String ip;

    @ApiModelProperty(value = "上传时间YYYY-MM-DD")
    private String uploadDateStr;
}
