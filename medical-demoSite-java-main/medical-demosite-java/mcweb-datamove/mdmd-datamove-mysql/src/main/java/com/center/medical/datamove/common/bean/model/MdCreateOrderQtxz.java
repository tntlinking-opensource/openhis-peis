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
 * 线上变更订单前台须知记录(MdCreateOrderQtxz)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:18
 */
@Data
@TableName("md_create_order_qtxz")
@ApiModel(value = "MdCreateOrderQtxz", description = "线上变更订单前台须知记录实体类")
public class MdCreateOrderQtxz extends Model<MdCreateOrderQtxz> implements Serializable {
    private static final long serialVersionUID = 977016862777069187L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "变更时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "订单ID")
    private String orderId;

    @ApiModelProperty(value = "变更人USERNAME")
    private String username;

    @ApiModelProperty(value = "修改后的前台须知")
    private String qtxz;

    @ApiModelProperty(value = "修改前的前台须知")
    private String oldQtxz;

    @ApiModelProperty(value = "第几次修改")
    private Integer idx;
}
