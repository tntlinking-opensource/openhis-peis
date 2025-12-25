package com.center.medical.system.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 版本控制-分中心版本更新记录(SysVersionDeploy)表实体类
 *
 * @author makejava
 * @since 2024-03-02 17:52:12
 */
@Data
@TableName("sys_version_deploy")
@ApiModel(value = "SysVersionDeploy", description = "版本控制-分中心版本更新记录实体类")
public class SysVersionDeploy extends Model<SysVersionDeploy> implements Serializable {
    private static final long serialVersionUID = -29960187709638636L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "版本id(关联sys_version_info的ID)")
    private Integer versionId;

    @ApiModelProperty(value = "服务IP地址")
    private String ipAddress;

    @ApiModelProperty(value = "版本更新包ID(关联sys_version_service的ID)")
    private Integer svsId;

    @ApiModelProperty(value = "服务名称")
    private String serviceName;

    @ApiModelProperty(value = "数据库更新文件(多文件)")
    private String sqlFile;

    @ApiModelProperty(value = "数据库更新状态：-1.更新失败 0.未更新 1.更新部分 2.全部更新")
    private Integer sqlStatus;

    @ApiModelProperty(value = "数据库更新备注")
    private String sqlRemark;

    @ApiModelProperty(value = "数据库更新时间")
    private Date sqlUpdateTime;

    @ApiModelProperty(value = "新版本安装包文件(单文件)")
    private String filePath;

    @ApiModelProperty(value = "安装更新状态：-2.忽略更新 -1.更新失败 0.未更新 1.更新中 2.已更新")
    private Integer fileStatus;

    @ApiModelProperty(value = "安装包更新备注")
    private String fileRemark;

    @ApiModelProperty(value = "安装包更新时间")
    private Date fileUpdateTime;

    @ApiModelProperty(value = "其他文件(多文件)")
    private String otherFile;

    @ApiModelProperty(value = "执行时间")
    private Date executeTime;

    @ApiModelProperty(value = "状态：-1.更新失败 0.待更新 1.更新中 2.已完成")
    private Integer status;

    @ApiModelProperty(value = "是否人工处理 ：0.否 1.是")
    private Integer isManual;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "执行结果")
    private String message;

    @ApiModelProperty(value = "人工处理人员")
    private String executer;

    @ApiModelProperty(value = "人工处理时间")
    private Date manualExecuteTime;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date modifydate;
}
