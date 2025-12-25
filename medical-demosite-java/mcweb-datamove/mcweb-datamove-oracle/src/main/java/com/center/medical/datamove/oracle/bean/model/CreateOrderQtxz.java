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
 * 线上变更订单前台须知记录(CreateOrderQtxz)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:17:54
 */
@Data
@TableName("CREATE_ORDER_QTXZ")
@ApiModel(value = "CreateOrderQtxz", description = "线上变更订单前台须知记录实体类")
public class CreateOrderQtxz extends Model<CreateOrderQtxz> implements Serializable {
    private static final long serialVersionUID = -89004749415409224L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "变更时间")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
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
    private String idx;
}
