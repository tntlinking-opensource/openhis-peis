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
 * 用户授权码(MdUserauthcode)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:49:26
 */
@Data
@TableName("md_userauthcode")
@ApiModel(value = "MdUserauthcode", description = "用户授权码实体类")
public class MdUserauthcode extends Model<MdUserauthcode> implements Serializable {
    private static final long serialVersionUID = -17062789266931030L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "授权码")
    private String authCode;
}
