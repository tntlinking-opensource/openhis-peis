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
 * 数据上传接收日志(SysUploadLog)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:39
 */
@Data
@TableName("sys_upload_log")
@ApiModel(value = "SysUploadLog", description = "数据上传接收日志实体类")
public class SysUploadLog extends Model<SysUploadLog> implements Serializable {
    private static final long serialVersionUID = 116856496225770046L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "分中心id")
    private String cid;

    @ApiModelProperty(value = "上传数据类型(SYS_UPLOAD_TYPE)代码")
    private Integer uploadType;

    @ApiModelProperty(value = "上传数据条数")
    private Integer uploadNum;

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
