package com.center.medical.system.bean.model;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 自动部署-版本更新分中心关联表(DeployVersionBranch)表实体类
 *
 * @author makejava
 * @since 2023-11-15 08:58:48
 */
@Data
@TableName("sys_deploy_version_branch")
@ApiModel(value = "DeployVersionBranch", description = "版本信息分中心关联表实体类")
public class DeployVersionBranch extends Model<DeployVersionBranch> implements Serializable {
    private static final long serialVersionUID = 168995982363338449L;

    @TableId(value = "id")
    @ApiModelProperty(value = "版本信息分中心关联表id")
    private Integer id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "分中心id")
    private String branchId;

    //sys_deploy_version.id
    @ApiModelProperty(value = "版本信息id")
    private Integer versionId;

    @ApiModelProperty(value = "是否删除 0否 1是")
    private Boolean isDelete;

    @ApiModelProperty(value = "数据库更新状态 0未更新 1更新成功 2更新失败")
    private Integer isSqlUpdated;

    @ApiModelProperty(value = "所有服务是否已更新 0否 1是")
    private Boolean isServiceUpdated;

    @ApiModelProperty(value = "数据库更新时间")
    private Date sqlUpdateTime;

    @ApiModelProperty(value = "所有服务完成更新时间")
    private Date serviceUpdateTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "分中心名称")
    private String fzx;
}
