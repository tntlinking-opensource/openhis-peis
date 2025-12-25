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
 * 总检医生-危害因素分类关联表 (MdUserHarmClass)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:49:17
 */
@Data
@TableName("md_user_harm_class")
@ApiModel(value = "MdUserHarmClass", description = "总检医生-危害因素分类关联表 实体类")
public class MdUserHarmClass extends Model<MdUserHarmClass> implements Serializable {
    private static final long serialVersionUID = -30856236666166280L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "分类id")
    private String classId;
}
