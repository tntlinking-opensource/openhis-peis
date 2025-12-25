package com.center.medical.system.bean.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统授权记录(SysAuthLog)表实体类
 *
 * @author makejava
 * @since 2024-01-17 20:20:02
 */
@Data
@TableName("sys_auth_log")
@ApiModel(value = "SysAuthLog", description = "系统授权记录实体类")
public class SysAuthLog extends Model<SysAuthLog> implements Serializable {
    private static final long serialVersionUID = 279162396397983807L;

    @TableId(value = "id")
    @ApiModelProperty(value = "记录ID")
    private Long id;

    @ApiModelProperty(value = "机构ID，对应sys_business_source中的source_id", required = true)
    private String institutionId;

    @ApiModelProperty("机构名称")
    private String institutionName;

    @ApiModelProperty(value = "授权类型：0.永久授权 1.有期限收取", required = true)
    private Integer authType;

    @ApiModelProperty(value = "授权码，对应sys_code_config中的auth_code", required = true)
    private String authCode;

    @ApiModelProperty(value = "授权密钥：授权信息经过rsa加密后的值")
    private String authValue;

    @ApiModelProperty(value = "授权备注")
    private String remark;

    @ApiModelProperty(value = "开始日期", required = true)
    private Date startTime;

    @ApiModelProperty(value = "到期时间", required = true)
    private Date expiredTime;

    @ApiModelProperty(value = "授权版本号")
    private String version;

    @ApiModelProperty(value = "到期是否可用：0.不可用 1.可用", required = true)
    private Integer canUse;

    @ApiModelProperty(value = "状态：-1已过期 0.失效 1.正常")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;
}
