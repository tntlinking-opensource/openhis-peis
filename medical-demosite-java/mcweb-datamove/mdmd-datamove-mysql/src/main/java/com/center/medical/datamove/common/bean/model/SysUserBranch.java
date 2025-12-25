package com.center.medical.datamove.common.bean.model;


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
 * 系统用户关联的分中心(SysUserBranch)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:39
 */
@Data
@TableName("sys_user_branch")
@ApiModel(value = "SysUserBranch", description = "系统用户关联的分中心实体类")
public class SysUserBranch extends Model<SysUserBranch> implements Serializable {
    private static final long serialVersionUID = -50208283121749040L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "关联ID")
    private String id;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "用户编号")
    private String userId;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
