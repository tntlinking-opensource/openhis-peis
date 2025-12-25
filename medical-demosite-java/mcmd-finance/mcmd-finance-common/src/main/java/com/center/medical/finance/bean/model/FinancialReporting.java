package com.center.medical.finance.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 财务提报(FinancialReporting)表实体类
 *
 * @author ay
 * @since 2023-06-28 16:52:39
 */
@Data
@TableName("md_financial_reporting")
@ApiModel(value = "FinancialReporting", description = "财务提报实体类")
public class FinancialReporting extends Model<FinancialReporting> implements Serializable {
    private static final long serialVersionUID = 894278492189604101L;

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "产值情况-全年目标(不含疫苗)")
    private Double outputvalueGoals;

    @ApiModelProperty(value = "利润情况-利润")
    private Double profitsituationProfit;

    @ApiModelProperty(value = "利润情况-全年目标(不含疫苗)")
    private Double profitsituationGoals;

    @ApiModelProperty(value = "现金流情况-现金净流量")
    private Double cashflowFlow;

    @ApiModelProperty(value = "现金流情况-资金安全警戒线")
    private Double cashflowCordon;

    @ApiModelProperty(value = "现金流情况-产值(不含疫苗)")
    private Double cashflowOutputvalue;

    @ApiModelProperty(value = "销售数据情况-超期应收账款(万元)")
    private Double saleoverdueAccounts;

    @ApiModelProperty(value = "人均情况-月平均人数 (含兼职)")
    private Double peopleNumber;

    @ApiModelProperty(value = "费用占比情况-销售费用")
    private Double costproportionSale;

    @ApiModelProperty(value = "费用占比情况-管理费用")
    private Double costproportionManage;

    @ApiModelProperty(value = "费用占比情况-房屋折旧、设备租赁")
    private Double costproportionHousing;

    @ApiModelProperty(value = "费用占比情况-人工成本")
    private Double costproportionPeople;

    @ApiModelProperty(value = "费用占比情况-提成")
    private Double costproportionCommission;

    @ApiModelProperty(value = "费用占比情况-试剂耗材")
    private Double costproportionConsumables;

    @ApiModelProperty(value = "费用占比情况-试剂耗材(不含疫苗)")
    private Double costproportionConsumablesNo;

    @ApiModelProperty(value = "选择时间，请选择提报月份，带着时分秒0点0分0秒")
    private Date choosedate;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "创建用户id")
    private String createUserid;

    @ApiModelProperty(value = "创建用户名")
    private String createUsername;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "修改用户id")
    private String modifyUserid;

    @ApiModelProperty(value = "修改用户名")
    private String modifyUsername;

    @ApiModelProperty(value = "中心id")
    private String cid;

    @ApiModelProperty(value = "销售数据情况-当期应收账款回收率")
    private Double saleoverdueRateOfRecovery;

    @ApiModelProperty(value = "销售数据情况-折扣率")
    private Double saleoverdueDiscountRate;

    @ApiModelProperty(value = "是否已删除：0.否 1.是")
    private Integer isDelete;

    @ApiModelProperty(value = "客户情况-新单金额")
    private Double customerNew;

    @ApiModelProperty(value = "客户情况-丢单金额")
    private Double customerLose;

    @ApiModelProperty(value = "客户情况-丢单金额(个检记账)")
    private Double customerLoseAccounting;

    @ApiModelProperty(value = "客户情况-大单金额(50万以上)")
    private Double customerBig50;

    @ApiModelProperty(value = "客户情况-50万以上大单个数")
    private Double customerBig50Count;

    @ApiModelProperty(value = "客户情况-大单金额(20万-50万)")
    private Double customerBig20;

    @ApiModelProperty(value = "客户情况-20万-50万大单个数")
    private Double customerBig20Count;

    @ApiModelProperty(value = "客户情况-个检记账")
    private Double customerAccounting;

    @ApiParam(hidden = true)
    @TableField(exist = false)
    @ApiModelProperty(value = "分中心")
    private String fzx;
}
