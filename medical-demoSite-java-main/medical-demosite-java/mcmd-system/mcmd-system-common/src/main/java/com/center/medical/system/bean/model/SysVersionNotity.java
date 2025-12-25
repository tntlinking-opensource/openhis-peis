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
 * 版本控制-新版本通知记录(SysVersionNotity)表实体类
 *
 * @author makejava
 * @since 2024-04-26 10:52:08
 */
@Data
@TableName("sys_version_notity")
@ApiModel(value = "SysVersionNotity", description = "版本控制-新版本通知记录实体类")
public class SysVersionNotity extends Model<SysVersionNotity> implements Serializable {
    private static final long serialVersionUID = 748158801348098083L;

    @TableId(value = "id")
    @ApiModelProperty(value = "通知记录ID")
    private Integer id;

    @ApiModelProperty(value = "版本号")
    private String versionId;

    @ApiModelProperty(value = "用户ID")
    private String userNo;

    @ApiModelProperty(value = "状态：0.未通知 1.已通知")
    private Integer status;

    @ApiModelProperty(value = "通知时间")
    private Date createTime;
}
