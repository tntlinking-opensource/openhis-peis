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
 * 各种接口加密信息(InterfaceAccount)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:06
 */
@Data
@TableName("md_interface_account")
@ApiModel(value = "InterfaceAccount", description = "各种接口加密信息实体类")
public class InterfaceAccount extends Model<InterfaceAccount> implements Serializable {
    private static final long serialVersionUID = -80241274856983397L;

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
