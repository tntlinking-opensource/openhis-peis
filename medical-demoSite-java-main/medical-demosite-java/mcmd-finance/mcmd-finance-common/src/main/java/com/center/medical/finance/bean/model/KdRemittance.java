package com.center.medical.finance.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * kingdeeremittance(KdRemittance)表实体类
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:43
 */
@Data
@TableName("kd_remittance")
@ApiModel(value = "KdRemittance", description = "kingdeeremittance实体类")
public class KdRemittance extends Model<KdRemittance> implements Serializable {
    private static final long serialVersionUID = -11509279005172673L;

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

    @ApiModelProperty(value = "是否线上创建：0.线下 1线上")
    private Integer online;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}
