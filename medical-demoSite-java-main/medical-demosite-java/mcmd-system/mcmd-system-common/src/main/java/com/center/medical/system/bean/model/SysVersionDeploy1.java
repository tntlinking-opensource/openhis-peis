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
 * 版本控制-更新记录(SysVersionDeploy1)表实体类
 *
 * @author makejava
 * @since 2024-01-23 10:36:18
 */
@Data
@TableName("sys_version_deploy")
@ApiModel(value = "SysVersionDeploy1", description = "版本控制-更新记录实体类")
public class SysVersionDeploy1 extends Model<SysVersionDeploy1> implements Serializable {
    private static final long serialVersionUID = -46398429193803499L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "版本信息ID(关联sys_deploy_version的ID)")
    private Integer versionId;

    @ApiModelProperty(value = "科室IP配置ID(关联md_ks_ip的ID)")
    private String ksIpId;

    @ApiModelProperty(value = "ip")
    private String address;

    @ApiModelProperty(value = "主机名")
    private String hostName;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "数据库更新状态：-1.更新失败 0.未更新 1.更新部分 2.全部更新")
    private Object sqlStatus;

    @ApiModelProperty(value = "数据库更新备注")
    private String sqlRemark;

    @ApiModelProperty(value = "数据库更新时间")
    private Date sqlUpdateTime;

    @ApiModelProperty(value = "是否人工处理  1是0否")
    private Object isManual;

    @ApiModelProperty(value = "执行时间")
    private Date executeTime;

    @ApiModelProperty(value = "状态：-1.更新失败 0.待更新 1.正在更新 2.已完成")
    private Integer status;

    @ApiModelProperty(value = "备注（人工处理）")
    private String remark;

    @ApiModelProperty(value = "执行结果")
    private String message;

    @ApiModelProperty(value = "人工处理人员")
    private String executer;

    @ApiModelProperty(value = "人工处理时间")
    private Date manualExecuteTime;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
