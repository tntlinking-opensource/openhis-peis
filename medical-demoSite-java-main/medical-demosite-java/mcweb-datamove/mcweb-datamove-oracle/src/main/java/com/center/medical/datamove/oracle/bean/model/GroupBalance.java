package com.center.medical.datamove.oracle.bean.model;


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
 * (GroupBalance)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:05
 */
@Data
@TableName("GROUP_BALANCE")
@ApiModel(value = "GroupBalance", description = "$tableInfo.comment实体类")
public class GroupBalance extends Model<GroupBalance> implements Serializable {
    private static final long serialVersionUID = -10499244080136443L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String orderId;

    @ApiModelProperty(value = "${column.comment}")
    private String typeId;

    @ApiModelProperty(value = "${column.comment}")
    private Double realityMoney;

    @ApiModelProperty(value = "${column.comment}")
    private Double prepaidAmount;

    @ApiModelProperty(value = "${column.comment}")
    private String getterId;

    @ApiModelProperty(value = "${column.comment}")
    private Date getterTime;

    @ApiModelProperty(value = "${column.comment}")
    private String memo;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isSquare;

    @ApiModelProperty(value = "${column.comment}")
    private Double returnMoney;

    @ApiModelProperty(value = "${column.comment}")
    private String groupId;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private Double shouldMoney;

    @ApiModelProperty(value = "${column.comment}")
    private String cardId;
}
