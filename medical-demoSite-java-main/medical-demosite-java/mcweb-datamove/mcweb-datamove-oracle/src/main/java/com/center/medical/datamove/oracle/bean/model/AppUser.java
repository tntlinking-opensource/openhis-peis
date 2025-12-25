package com.center.medical.datamove.oracle.bean.model;


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
 * (AppUser)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:03
 */
@Data
@TableName("APP_USER")
@ApiModel(value = "AppUser", description = "$tableInfo.comment实体类")
public class AppUser extends Model<AppUser> implements Serializable {
    private static final long serialVersionUID = -32134386881989454L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String password;

    @ApiModelProperty(value = "${column.comment}")
    private String name;

    @ApiModelProperty(value = "${column.comment}")
    private String sex;

    @ApiModelProperty(value = "${column.comment}")
    private String idcard;

    @ApiModelProperty(value = "${column.comment}")
    private String provinceId;

    @ApiModelProperty(value = "${column.comment}")
    private String cityId;

    @ApiModelProperty(value = "${column.comment}")
    private String countyId;

    @ApiModelProperty(value = "${column.comment}")
    private String address;

    @ApiModelProperty(value = "${column.comment}")
    private String birthday;

    @ApiModelProperty(value = "${column.comment}")
    private String idMarriage;

    @ApiModelProperty(value = "${column.comment}")
    private String cid;

    @ApiModelProperty(value = "${column.comment}")
    private String devicetype;

    @ApiModelProperty(value = "${column.comment}")
    private String tel;

    @ApiModelProperty(value = "${column.comment}")
    private String daId;

    @ApiModelProperty(value = "${column.comment}")
    private String picture;

    @ApiModelProperty(value = "${column.comment}")
    private String openid;

    @ApiModelProperty(value = "${column.comment}")
    private String sessionKey;

    @ApiModelProperty(value = "${column.comment}")
    private String unionid;

    @ApiModelProperty(value = "${column.comment}")
    private String nickName;

    @ApiModelProperty(value = "${column.comment}")
    private Integer gender;

    @ApiModelProperty(value = "${column.comment}")
    private String avatarUrl;

    @ApiModelProperty(value = "${column.comment}")
    private String country;

    @ApiModelProperty(value = "${column.comment}")
    private String city;

    @ApiModelProperty(value = "${column.comment}")
    private String province;

    @ApiModelProperty(value = "${column.comment}")
    private Date lastLoginTime;

    @ApiModelProperty(value = "${column.comment}")
    private Date regtime;

    @ApiModelProperty(value = "${column.comment}")
    private Integer regtype;

    @ApiModelProperty(value = "${column.comment}")
    private Integer lastlogintype;

    @ApiModelProperty(value = "${column.comment}")
    private String money;
}
