package com.center.medical.system.bean.model;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统服务(记录系统服务种类)(SysServiceType1)表实体类
 *
 * @author makejava
 * @since 2024-01-23 11:02:55
 */
@Data
@TableName("sys_service_type")
@ApiModel(value = "SysServiceType1", description = "系统服务(记录系统服务种类)实体类")
public class SysServiceType1 extends Model<SysServiceType1> implements Serializable {
    private static final long serialVersionUID = 155056304070304031L;

    @TableId(value = "service_id")
    @ApiModelProperty(value = "服务ID")
    private Integer serviceId;

    @ApiModelProperty(value = "服务名称")
    private String serviceName;

    @ApiModelProperty(value = "状态：0.正常 1.停用")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
