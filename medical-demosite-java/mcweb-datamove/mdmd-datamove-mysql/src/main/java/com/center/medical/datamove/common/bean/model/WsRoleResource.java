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
 * 角色-资源映射表(WsRoleResource)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:41
 */
@Data
@TableName("ws_role_resource")
@ApiModel(value = "WsRoleResource", description = "角色-资源映射表实体类")
public class WsRoleResource extends Model<WsRoleResource> implements Serializable {
    private static final long serialVersionUID = 421035521310451250L;

    @TableId(value = "role_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "资源id")
    private String resourceId;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
