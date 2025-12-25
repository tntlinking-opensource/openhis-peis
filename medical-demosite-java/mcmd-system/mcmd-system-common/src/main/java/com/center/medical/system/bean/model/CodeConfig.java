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
 * 加密密钥及授权码表(CodeConfig)表实体类
 *
 * @author 路飞船长
 * @since 2023-04-04 19:18:43
 */
@Data
@TableName("sys_code_config")
@ApiModel(value = "CodeConfig", description = "加密密钥及授权码表实体类")
public class CodeConfig extends Model<CodeConfig> implements Serializable {
    private static final long serialVersionUID = 243242027688433310L;

    @TableId(value = "id")
    @ApiModelProperty(value = "记录ID")
    private Long id;

    @ApiModelProperty(value = "授权码类型：0.系统提供 1.外部合作商提供")
    private Integer codeType;

    @ApiModelProperty(value = "授权码（16位）")
    private String authCode;

    @ApiModelProperty(value = "业务方的授权码")
    private String bizAuthCode;

    @ApiModelProperty(value = "授权业务标识")
    private String bsFlag;

    @ApiModelProperty(value = "合作第三方ID（关联sys_business_source.source_id）")
    private String sourceId;

    @ApiModelProperty(value = "记录说明")
    private String remark;

    @ApiModelProperty(value = "参数设置对象")
    private String codeSetting;

    @ApiModelProperty(value = "状态：0.无效 1.正常")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "键，如：加密公钥（key与bs_flag组成唯一值）")
    private String keyText;

    @ApiModelProperty(value = "值，如：解密私钥")
    private String valueText;

    @ApiModelProperty(value = "有效期")
    private Date expiryDate;
}
