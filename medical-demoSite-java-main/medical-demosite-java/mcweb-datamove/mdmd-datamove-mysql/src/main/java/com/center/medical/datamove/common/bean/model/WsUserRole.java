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
 * 网站用户权限(WsUserRole)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:43
 */
@Data
@TableName("ws_user_role")
@ApiModel(value = "WsUserRole", description = "网站用户权限实体类")
public class WsUserRole extends Model<WsUserRole> implements Serializable {
    private static final long serialVersionUID = 474209713138342374L;

    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "管理员id")
    private String adminId;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
