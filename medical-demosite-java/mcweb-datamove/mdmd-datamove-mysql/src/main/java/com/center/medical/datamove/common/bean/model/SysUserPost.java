package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 用户与岗位关联表(SysUserPost)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:40
 */
@Data
@TableName("sys_user_post")
@ApiModel(value = "SysUserPost", description = "用户与岗位关联表实体类")
public class SysUserPost extends Model<SysUserPost> implements Serializable {
    private static final long serialVersionUID = -10240262895568424L;

    @TableId(value = "user_id")
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "岗位ID")
    private Long postId;
}
