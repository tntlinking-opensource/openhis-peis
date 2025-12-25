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
 * 年超20万额度单位统计(LargeAmountGroupStatistics)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:06
 */
@Data
@TableName("md_large_amount_group_statistics")
@ApiModel(value = "LargeAmountGroupStatistics", description = "年超20万额度单位统计实体类")
public class LargeAmountGroupStatistics extends Model<LargeAmountGroupStatistics> implements Serializable {
    private static final long serialVersionUID = -70181112824812508L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "年份")
    private String year;

    @ApiModelProperty(value = "金额")
    private Double money;

    @ApiModelProperty(value = "单位id")
    private String orgId;

    @ApiModelProperty(value = "销售员USERNAME")
    private String salesmanUsername;

    @ApiModelProperty(value = "总订单数量")
    private Double orderCount;
}
