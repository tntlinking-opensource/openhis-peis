package com.center.medical.app.bean.model;


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


    @ApiModelProperty(value = "创建时间")
    private Date createdate;


    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

}
