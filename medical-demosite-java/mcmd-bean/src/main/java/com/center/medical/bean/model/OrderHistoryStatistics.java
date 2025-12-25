package com.center.medical.bean.model;


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
 * 订单历史平均折扣、变动成本率统计表(OrderHistoryStatistics)表实体类
 *
 * @author ay
 * @since 2023-12-07 14:05:37
 */
@Data
@TableName("md_order_history_statistics")
@ApiModel(value = "OrderHistoryStatistics", description = "订单历史平均折扣、变动成本率统计表实体类")
public class OrderHistoryStatistics extends Model<OrderHistoryStatistics> implements Serializable {
    private static final long serialVersionUID = 989915405552146689L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;


    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;


    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;


    @ApiModelProperty(value = "单位id")
    private Integer intId;


    @ApiModelProperty(value = "年份yyyy")
    private String year;


    @ApiModelProperty(value = "分中心id")
    private String fzxId;


    @ApiModelProperty(value = "总实收")
    private Double totalPaid;


    @ApiModelProperty(value = "平均折扣率")
    private Double averageDiscountRate;


    @ApiModelProperty(value = "变动成本率")
    private Double variableCostRate;


    @ApiModelProperty(value = "${column.comment}")
    private String idOrg;

}
