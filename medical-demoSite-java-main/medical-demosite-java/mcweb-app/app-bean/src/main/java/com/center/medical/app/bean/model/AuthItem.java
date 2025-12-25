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
 * 外部接口授权记录(AuthItem)表实体类
 *
 * @author makejava
 * @since 2023-09-22 11:40:05
 */
@Data
@TableName("md_auth_item")
@ApiModel(value = "AuthItem", description = "外部接口授权记录实体类")
public class AuthItem extends Model<AuthItem> implements Serializable {
    private static final long serialVersionUID = -62337722660734860L;

    @TableId(value = "id")
    @ApiModelProperty(value = "授权记录ID")
    private Integer id;

    @ApiModelProperty(value = "授权标识")
    private String authFlag;

    @ApiModelProperty(value = "授权对象名称")
    private String authName;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "配置设置")
    private String setting;

    @ApiModelProperty(value = "到期时间")
    private Date expiryDate;

    @ApiModelProperty(value = "状态：0.停用 1.正常")
    private Integer status;

    @ApiModelProperty(value = "创建者")
    private String creater;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updater;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
