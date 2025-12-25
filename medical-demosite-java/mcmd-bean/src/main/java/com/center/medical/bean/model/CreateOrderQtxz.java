package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 线上变更订单前台须知记录(CreateOrderQtxz)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:57
 */
@Data
@TableName("md_create_order_qtxz")
@ApiModel(value = "CreateOrderQtxz", description = "线上变更订单前台须知记录实体类")
public class CreateOrderQtxz extends Model<CreateOrderQtxz> implements Serializable {
    private static final long serialVersionUID = 177085143125488941L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "变更时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
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


    public CreateOrderQtxz(Integer idx, String qtxz, String oldQtxz, String username, String orderId) {
        this.idx = idx;
        this.qtxz = qtxz;
        this.oldQtxz = oldQtxz;
        this.username = username;
        this.orderId = orderId;
    }

    public CreateOrderQtxz() {
    }
}
