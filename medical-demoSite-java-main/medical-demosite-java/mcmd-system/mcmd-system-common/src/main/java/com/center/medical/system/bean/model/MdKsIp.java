package com.center.medical.system.bean.model;


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
 * 科室IP(MdKsIp)表实体类
 *
 * @author makejava
 * @since 2023-11-15 10:27:19
 */
@Data
@TableName("md_ks_ip")
@ApiModel(value = "MdKsIp", description = "科室IP实体类")
public class MdKsIp extends Model<MdKsIp> implements Serializable {
    private static final long serialVersionUID = -78550653193235600L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "科室名称")
    private String name;

    @ApiModelProperty(value = "IP地址：IP+端口号")
    private String ip;

    @ApiModelProperty(value = "关联的科室ID")
    private String ksId;

    @ApiModelProperty(value = "科室本地图片存储路径")
    private String localPath;

    @ApiModelProperty(value = "科室本地备份路径")
    private String backupPath;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "状态：0.关闭 1.启用")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否开启自动更新 0否 1是")
    private Boolean isEnableUpdate;

    @ApiModelProperty(value = "更新类型 详见com.center.medical.bean.enums.DeployType")
    private Integer updateType;

    @ApiModelProperty(value = "更新服务启动命令")
    private String deployStartupCommand;

    @ApiModelProperty(value = "服务启动命令")
    private String serviceStartupCommand;

    @ApiModelProperty(value = "更新服务地址")
    private String deployServiceAddr;

    @ApiModelProperty(value = "下载到本地后的jar包位置，带文件名字。(没用到)")
    private String jarPath;

    @ApiModelProperty(value = "被更新服务地址")
    private String serviceAddr;
}
