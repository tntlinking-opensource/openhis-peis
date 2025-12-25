package com.center.medical.appadmin.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 随行支付配置参数(SuixingPayConfig)表实体类
 *
 * @author ay
 * @since 2024-07-12 17:05:40
 */
@Data
@TableName("md_suixing_pay_config")
@ApiModel(value = "SuixingPayConfig", description = "随行支付配置参数实体类")
public class SuixingPayConfig extends Model<SuixingPayConfig> implements Serializable {
    private static final long serialVersionUID = 667541558877329443L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "记录ID")
    private String id;


    @ApiModelProperty(value = "分中心id")
    private String branchId;


    @ApiModelProperty(value = "机构编号")
    private String orgId;


    @ApiModelProperty(value = "商户编号")
    private String mno;


    @ApiModelProperty(value = "私钥")
    private String privateKey;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;


    @TableField(exist = false)
    @ApiModelProperty(value = "分中心名称")
    private String branchName;

}
