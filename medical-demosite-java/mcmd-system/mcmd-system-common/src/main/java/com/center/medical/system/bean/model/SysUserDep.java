package com.center.medical.system.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户关联的科室(SysUserDep)表实体类
 *
 * @author ay
 * @since 2023-04-06 13:36:14
 */
@Data
@TableName("sys_user_dep")
@ApiModel(value = "SysUserDep", description = "系统用户关联的科室实体类")
public class SysUserDep extends Model<SysUserDep> implements Serializable {
    private static final long serialVersionUID = -86374574849857630L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "用户编号")
    private String userId;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
