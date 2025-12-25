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
 * 用户-销售关联表 (MdUserSaleer)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:49:24
 */
@Data
@TableName("md_user_saleer")
@ApiModel(value = "MdUserSaleer", description = "用户-销售关联表 实体类")
public class MdUserSaleer extends Model<MdUserSaleer> implements Serializable {
    private static final long serialVersionUID = -56530243921356508L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "销售（md5）")
    private String saleerMd5;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
