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
 * 操作日志(MdOperateLog)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:29
 */
@Data
@TableName("md_operate_log")
@ApiModel(value = "MdOperateLog", description = "操作日志实体类")
public class MdOperateLog extends Model<MdOperateLog> implements Serializable {
    private static final long serialVersionUID = 460680502364716812L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "操作人")
    private String operator;

    @ApiModelProperty(value = "操作方法")
    private String method;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "操作类型")
    private String opType;
}
