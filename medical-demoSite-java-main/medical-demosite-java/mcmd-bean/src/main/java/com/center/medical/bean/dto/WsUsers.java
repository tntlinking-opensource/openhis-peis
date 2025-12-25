package com.center.medical.bean.dto;


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
 * 网站用户(WsUsers)表实体类
 *
 * @author ay
 * @since 2024-05-29 16:31:10
 */
@Data
@TableName("ws_users")
@ApiModel(value = "WsUsers", description = "网站用户实体类")
public class WsUsers extends Model<WsUsers> implements Serializable {
    private static final long serialVersionUID = 453048207475939795L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;


    @ApiModelProperty(value = "用户名")
    private String username;


    @ApiModelProperty(value = "密码")
    private String password;


    @ApiModelProperty(value = "姓名")
    private String name;


    @ApiModelProperty(value = "邮箱")
    private String email;


    @ApiModelProperty(value = "创建时间")
    private Date createdate;


    @ApiModelProperty(value = "更新时间")
    private Date modifydate;


    @ApiModelProperty(value = "最后登录日期")
    private Date logindate;


    @ApiModelProperty(value = "最后登录IP")
    private String loginip;


    @ApiModelProperty(value = "账号是否未过期")
    private String useraccountnonexpired;


    @ApiModelProperty(value = "账号是否未锁定")
    private String useraccountnonlocked;


    @ApiModelProperty(value = "账号是否可用")
    private String userenabled;


    @ApiModelProperty(value = "账号凭证是否未过期")
    private String usercredentialsnonexpired;


    @ApiModelProperty(value = "部门id")
    private String depid;


    @ApiModelProperty(value = "是否为系统内置")
    private String issystem;


    @ApiModelProperty(value = "是否领导")
    private String isleader;


    @ApiModelProperty(value = "分中心id")
    private String cid;


    @ApiModelProperty(value = "是否删除 0未删除 1 已删除")
    private Integer isDelete;


    @ApiModelProperty(value = "输入码")
    private String inputCode;


    @ApiModelProperty(value = "电话")
    private String phone;


    @ApiModelProperty(value = "is_doc")
    private Integer isDoc;


    @ApiModelProperty(value = "客户ID")
    private String customerId;


    @ApiModelProperty(value = "ishelp")
    private Integer ishelp;



    public WsUsers() {

    }

    public WsUsers(String username, String password, String name,
                     String email, String userAccountNonExpired,
                     String userAccountNonLocked, String userCredentialsNonExpired,
                     String userEnabled, String isSystem, Integer isDelete,
                     String inputCode, String customerId, String phone,String depId) {
        super();
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.useraccountnonexpired = userAccountNonExpired;
        this.useraccountnonlocked = userAccountNonLocked;
        this.usercredentialsnonexpired = userCredentialsNonExpired;
        this.userenabled = userEnabled;
        this.issystem = isSystem;
        this.isDelete= isDelete;
        this.inputCode = inputCode;
        this.customerId = customerId;
        this.phone = phone;
        this.depid=depId;
    }

}
