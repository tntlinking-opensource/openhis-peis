package com.center.medical.datamove.oracle.bean.model;


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
 * (TradeRecord)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:39
 */
@Data
@TableName("TRADE_RECORD")
@ApiModel(value = "TradeRecord", description = "$tableInfo.comment实体类")
public class TradeRecord extends Model<TradeRecord> implements Serializable {
    private static final long serialVersionUID = -57883148026043850L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private String outTradeNo;

    @ApiModelProperty(value = "${column.comment}")
    private Double money;

    @ApiModelProperty(value = "${column.comment}")
    private String note;

    @ApiModelProperty(value = "${column.comment}")
    private Integer tradeType;

    @ApiModelProperty(value = "${column.comment}")
    private String tempItemIds;

    @ApiModelProperty(value = "${column.comment}")
    private Integer status;

    @ApiModelProperty(value = "登记员ID ")
    private String registerId;

    @ApiModelProperty(value = "订单类型 0 等级 1 加项")
    private Double orderType;
}
