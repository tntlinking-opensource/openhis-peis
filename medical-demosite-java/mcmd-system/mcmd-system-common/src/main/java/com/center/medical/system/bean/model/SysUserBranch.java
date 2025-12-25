package com.center.medical.system.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户关联的分中心(SysUserBranch)表实体类
 *
 * @author 路飞船长
 * @since 2023-05-20 15:27:58
 */
@Data
@TableName("sys_user_branch")
@ApiModel(value = "SysUserBranch", description = "系统用户关联的分中心实体类")
public class SysUserBranch extends Model<SysUserBranch> implements Serializable {
    private static final long serialVersionUID = -73233558886646873L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "关联ID")
    private String id;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "用户编号")
    private String userId;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
