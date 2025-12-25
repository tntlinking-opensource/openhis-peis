package com.center.medical.reception.bean.model;


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
 * 订单与分中心关联表(Orderandfzx)表实体类
 *
 * @author ay
 * @since 2023-07-25 21:10:27
 */
@Data
@TableName("ORDERANDFZX")
@ApiModel(value = "Orderandfzx", description = "订单与分中心关联表实体类")
public class OrOrderandfzx extends Model<OrOrderandfzx> implements Serializable {
    private static final long serialVersionUID = 477364894279650134L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "订单ID")
    private String ddid;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "同步状态(0:未同步、1:同步、2:更新)")
    private Integer tbzt;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "备单日期")
    private Date bdrq;

    @ApiModelProperty(value = "${column.comment}")
    private Integer xzzt;

    @ApiModelProperty(value = "${column.comment}")
    private Date xzsj;

    @ApiModelProperty(value = "${column.comment}")
    private Integer ddxzzt;

    @ApiModelProperty(value = "${column.comment}")
    private Integer xzxzzt;

    @ApiModelProperty(value = "${column.comment}")
    private Double ycxzzt;

    @ApiModelProperty(value = "${column.comment}")
    private Double kdzlzt;
}
