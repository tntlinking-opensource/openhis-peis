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
 * 年超20万额度单位统计(LargeAmountGroupStatistics)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:34
 */
@Data
@TableName("LARGE_AMOUNT_GROUP_STATISTICS")
@ApiModel(value = "LargeAmountGroupStatistics", description = "年超20万额度单位统计实体类")
public class LargeAmountGroupStatistics extends Model<LargeAmountGroupStatistics> implements Serializable {
    private static final long serialVersionUID = 891659442824784764L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String year;

    @ApiModelProperty(value = "${column.comment}")
    private Double money;

    @ApiModelProperty(value = "${column.comment}")
    private String orgId;

    @ApiModelProperty(value = "销售员USERNAME")
    private String salesmanUsername;

    @ApiModelProperty(value = "总订单数量")
    private String orderCount;
}
