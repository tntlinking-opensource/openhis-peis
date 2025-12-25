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
 * 各种接口加密信息(MdInterfaceAccount)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:22
 */
@Data
@TableName("md_interface_account")
@ApiModel(value = "MdInterfaceAccount", description = "各种接口加密信息实体类")
public class MdInterfaceAccount extends Model<MdInterfaceAccount> implements Serializable {
    private static final long serialVersionUID = 976387257734973894L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码（固定32位）")
    private String password;

    @ApiModelProperty(value = "密钥")
    private String key;
}
