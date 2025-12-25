package com.center.medical.system.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 总检医生-危害因素分类关联表 (UserHarmClass)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:18
 */
@Data
@TableName("md_user_harm_class")
@ApiModel(value = "UserHarmClass", description = "总检医生-危害因素分类关联表 实体类")
public class UserHarmClass extends Model<UserHarmClass> implements Serializable {
    private static final long serialVersionUID = -79712586491555681L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "分类id")
    private String classId;
}
