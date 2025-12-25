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
 * 交易记录(MdTradeRecord)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:49:12
 */
@Data
@TableName("md_trade_record")
@ApiModel(value = "MdTradeRecord", description = "交易记录实体类")
public class MdTradeRecord extends Model<MdTradeRecord> implements Serializable {
    private static final long serialVersionUID = 591487179723069624L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "第三方交易流水号")
    private String outTradeNo;

    @ApiModelProperty(value = "交易额")
    private Double money;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "交易类型")
    private Integer tradeType;

    @ApiModelProperty(value = "temp_item_ids")
    private String tempItemIds;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "登记员ID")
    private String registerId;

    @ApiModelProperty(value = "订单类型 0 等级 1 加项")
    private Integer orderType;
}
