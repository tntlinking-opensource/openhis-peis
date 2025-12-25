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
 * 微信小程序操作日志(AppLog)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:44
 */
@Data
@TableName("APP_LOG")
@ApiModel(value = "AppLog", description = "微信小程序操作日志实体类")
public class AppLog extends Model<AppLog> implements Serializable {
    private static final long serialVersionUID = 837193176043390498L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "操作类型 1.弃检  2.补检")
    private String operateType;

    @ApiModelProperty(value = "签名图位置")
    private String picturePath;

    @ApiModelProperty(value = "操作app用户id")
    private String userId;

    @ApiModelProperty(value = "项目id")
    private String itemId;

    @ApiModelProperty(value = "操作时间")
    private Date operateTime;

    @ApiModelProperty(value = "图片删除状态，没用到的图片定期删除 1已删除  0未删除")
    private Integer isDelete;
}
