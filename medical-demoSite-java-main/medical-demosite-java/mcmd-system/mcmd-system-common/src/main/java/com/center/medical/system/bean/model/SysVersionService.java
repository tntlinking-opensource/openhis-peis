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
 * 版本控制-系统服务更新包记录(SysVersionService)表实体类
 *
 * @author makejava
 * @since 2024-03-01 18:02:38
 */
@Data
@TableName("sys_version_service")
@ApiModel(value = "SysVersionService", description = "版本控制-系统服务更新包记录实体类")
public class SysVersionService extends Model<SysVersionService> implements Serializable {
    private static final long serialVersionUID = 655215729053530833L;

    @TableId(value = "id")
    @ApiModelProperty(value = "版本更新包ID")
    private Integer id;

    @ApiModelProperty(value = "系统服务ID（关联数据表sys_service_type的ID）")
    private Integer serviceId;

    @ApiModelProperty(value = "服务名称")
    private String serviceName;

    @ApiModelProperty(value = "版本ID(关联sys_version_info的ID)")
    private Integer versionId;

    @ApiModelProperty(value = "服务类型：1.前端vue 2.后端java")
    private Integer serviceType;

    @ApiModelProperty(value = "新版本安装包文件(单文件)")
    private String filePath;

    @ApiModelProperty(value = "数据库更新文件(多文件)")
    private String sqlFile;

    @ApiModelProperty(value = "其他文件(多文件)")
    private String otherFile;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态：0.正常 1.停用")
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
}
