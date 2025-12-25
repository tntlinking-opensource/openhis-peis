package com.center.medical.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 网站用户权限(WsUserRole)表实体类
 *
 * @author ay
 * @since 2024-05-29 17:02:23
 */
@Data
@TableName("ws_user_role")
@ApiModel(value = "WsUserRole", description = "网站用户权限实体类")
public class WsUserRole extends Model<WsUserRole> implements Serializable {
    private static final long serialVersionUID = -23825401334339760L;


    @ApiModelProperty(value = "用户id")
    private Long userId;


    @ApiModelProperty(value = "角色id")
    private Long roleId;


    public WsUserRole(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }


    public WsUserRole() {
    }
}
