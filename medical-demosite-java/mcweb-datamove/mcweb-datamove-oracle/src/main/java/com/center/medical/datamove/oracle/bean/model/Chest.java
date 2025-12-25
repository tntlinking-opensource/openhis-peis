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
 * 此表为通用表，团检(Chest)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:17:37
 */
@Data
@TableName("CHEST")
@ApiModel(value = "Chest", description = "此表为通用表，团检实体类")
public class Chest extends Model<Chest> implements Serializable {
    private static final long serialVersionUID = -78727029441165325L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检类型")
    private String tjlx;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "柜子号")
    private String gzh;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更改日期")
    private Date modifydate;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;
}
