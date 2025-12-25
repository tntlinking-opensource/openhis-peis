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
 * 自动部署-版本更新分中心关联表(SysVersionBranch1)表实体类
 *
 * @author makejava
 * @since 2024-01-23 10:36:17
 */
@Data
@TableName("sys_version_branch")
@ApiModel(value = "SysVersionBranch1", description = "自动部署-版本更新分中心关联表实体类")
public class SysVersionBranch1 extends Model<SysVersionBranch1> implements Serializable {
    private static final long serialVersionUID = -89878896704623903L;

    @TableId(value = "id")
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "版本信息ID(关联sys_deploy_version的ID)")
    private Integer versionId;

    @ApiModelProperty(value = "是否删除：0.否 1.是")
    private Object deleted;

    @ApiModelProperty(value = "状态：-1.执行失败 0.待执行 1.已执行部分 2已完成")
    private Object status;

    @ApiModelProperty(value = "完成时间")
    private Date finishTime;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
