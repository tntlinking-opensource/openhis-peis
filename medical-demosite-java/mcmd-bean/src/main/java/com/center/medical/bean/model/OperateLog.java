package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志(OperateLog)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:07
 */
@Data
@TableName("md_operate_log")
@ApiModel(value = "OperateLog", description = "操作日志实体类")
public class OperateLog extends Model<OperateLog> implements Serializable {
    private static final long serialVersionUID = 309432056953785522L;

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
