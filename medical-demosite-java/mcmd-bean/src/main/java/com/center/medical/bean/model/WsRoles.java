package com.center.medical.bean.model;


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
 * 网站角色(WsRoles)表实体类
 *
 * @author ay
 * @since 2024-05-29 16:24:13
 */
@Data
@TableName("ws_roles")
@ApiModel(value = "WsRoles", description = "网站角色实体类")
public class WsRoles extends Model<WsRoles> implements Serializable {
    private static final long serialVersionUID = -26614122683877070L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;


    @ApiModelProperty(value = "角色名称")
    private String name;


    @ApiModelProperty(value = "创建时间")
    private Date createdate;


    @ApiModelProperty(value = "更新时间")
    private Date modifydate;


    @ApiModelProperty(value = "描述")
    private String description;


    @ApiModelProperty(value = "是否系统内置")
    private String issystem;


    @ApiModelProperty(value = "角色类型")
    private String roleType;

}
