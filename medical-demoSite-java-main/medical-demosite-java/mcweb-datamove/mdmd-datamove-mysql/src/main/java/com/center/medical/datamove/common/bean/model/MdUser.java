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
 * 体检用户账号(MdUser)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:49:15
 */
@Data
@TableName("md_user")
@ApiModel(value = "MdUser", description = "体检用户账号实体类")
public class MdUser extends Model<MdUser> implements Serializable {
    private static final long serialVersionUID = 769025837981941915L;

    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "姓名")
    private String nickName;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "支付密码")
    private String payPassword;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "档案ID")
    private String archiveId;

    @ApiModelProperty(value = "备注")
    private String userMemo;

    @ApiModelProperty(value = "激活状态")
    private String status;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
