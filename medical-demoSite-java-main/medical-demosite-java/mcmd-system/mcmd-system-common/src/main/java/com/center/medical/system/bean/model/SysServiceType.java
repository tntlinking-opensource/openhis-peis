package com.center.medical.system.bean.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统服务(记录系统服务种类)(SysServiceType)表实体类
 *
 * @author makejava
 * @since 2024-03-01 18:02:34
 */
@Data
@TableName("sys_service_type")
@ApiModel(value = "SysServiceType", description = "系统服务(记录系统服务种类)实体类")
public class SysServiceType extends Model<SysServiceType> implements Serializable {
    private static final long serialVersionUID = 337946017043480268L;

    @TableId(value = "service_id")
    @ApiModelProperty(value = "服务ID")
    private Integer serviceId;

    @ApiModelProperty(value = "服务名称")
    private String serviceName;

    @ApiModelProperty(value = "服务类型：1.前端vue 2.后端java")
    private Integer serviceType;

    @ApiModelProperty(value = "是否人工处理 ：0.否 1.是")
    private Integer isManual;

    @ApiModelProperty(value = "状态：0.正常 1.停用")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
}
