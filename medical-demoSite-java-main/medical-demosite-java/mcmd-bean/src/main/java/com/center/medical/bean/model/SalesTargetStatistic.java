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
 * 销售目标自动统计(SalesTargetStatistic)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:11
 */
@Data
@TableName("md_sales_target_statistic")
@ApiModel(value = "SalesTargetStatistic", description = "销售目标自动统计实体类")
public class SalesTargetStatistic extends Model<SalesTargetStatistic> implements Serializable {
    private static final long serialVersionUID = 702951391056290850L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userid;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "实际完成额")
    private Double complete;

    @ApiModelProperty(value = "1月实际完成额")
    private Double complete1;

    @ApiModelProperty(value = "2月实际完成额")
    private Double complete2;

    @ApiModelProperty(value = "3月实际完成额")
    private Double complete3;

    @ApiModelProperty(value = "4月实际完成额")
    private Double complete4;

    @ApiModelProperty(value = "5月实际完成额")
    private Double complete5;

    @ApiModelProperty(value = "6月实际完成额")
    private Double complete6;

    @ApiModelProperty(value = "7月实际完成额")
    private Double complete7;

    @ApiModelProperty(value = "8月实际完成额")
    private Double complete8;

    @ApiModelProperty(value = "9月实际完成额")
    private Double complete9;

    @ApiModelProperty(value = "10实际完成额")
    private Double complete10;

    @ApiModelProperty(value = "11月实际完成额")
    private Double complete11;

    @ApiModelProperty(value = "12实际完成额")
    private Double complete12;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "中心id")
    private String cid;

    @ApiModelProperty(value = "年")
    private String year;

    @ApiModelProperty(value = "类型 0年度，1季度，2月度")
    private Integer type;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
