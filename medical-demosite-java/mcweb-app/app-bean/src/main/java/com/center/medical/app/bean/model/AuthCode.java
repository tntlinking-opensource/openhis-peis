package com.center.medical.app.bean.model;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 加密密钥及授权码表(AuthCode)表实体类
 *
 * @author makejava
 * @since 2023-09-22 11:40:05
 */
@Data
@TableName("md_auth_code")
@ApiModel(value = "AuthCode", description = "加密密钥及授权码表实体类")
public class AuthCode extends Model<AuthCode> implements Serializable {
    private static final long serialVersionUID = -10878672850354422L;

    @TableId(value = "id")
    @ApiModelProperty(value = "记录ID")
    private Long id;

    @ApiModelProperty(value = "授权码类型：0.系统提供 1.外部合作商提供")
    private Integer codeType;

    @ApiModelProperty(value = "授权码（32位）")
    private String authCode;

    @ApiModelProperty(value = "业务方的授权码")
    private String bizAuthCode;

    @ApiModelProperty(value = "键，如：加密公钥（key与auth_flag组成唯一值）")
    private String keyText;

    @ApiModelProperty(value = "值，如：解密私钥")
    private String valueText;

    @ApiModelProperty(value = "业务标识")
    private String authFlag;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "到期时间")
    private Date expiryDate;

    @ApiModelProperty(value = "状态：0.无效 1.正常")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
