package com.center.medical.system.bean.model;


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
 * 系统业务功能(SysFunction)表实体类
 *
 * @author makejava
 * @since 2024-03-19 11:12:08
 */
@Data
@TableName("sys_function")
@ApiModel(value = "SysFunction", description = "系统业务功能实体类")
public class SysFunction extends Model<SysFunction> implements Serializable {
    private static final long serialVersionUID = -97203123087177187L;

    @TableId(value = "function_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "业务功能ID")
    private String functionId;

    @ApiModelProperty(value = "业务功能名称")
    private String functionName;

    @ApiModelProperty(value = "状态：0.正常 1.停用")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
