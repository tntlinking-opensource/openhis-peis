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
 * kingdeeremittance(KdRemittance)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:04
 */
@Data
@TableName("kd_remittance")
@ApiModel(value = "KdRemittance", description = "kingdeeremittance实体类")
public class KdRemittance extends Model<KdRemittance> implements Serializable {
    private static final long serialVersionUID = -17725271718696905L;

    @TableId(value = "fid", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "金蝶主键值")
    private String fid;

    @ApiModelProperty(value = "对方账户编号")
    private String transactionnumber;

    @ApiModelProperty(value = "对方户名")
    private String remitter;

    @ApiModelProperty(value = "收入金额")
    private String income;

    @ApiModelProperty(value = "交易日期")
    private Date transdate;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;
}
