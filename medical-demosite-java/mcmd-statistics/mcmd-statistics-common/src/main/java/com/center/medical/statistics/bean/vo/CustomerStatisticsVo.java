package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 来检客户统计 返回数据
 */
@Data
public class CustomerStatisticsVo implements Serializable {
    private static final long serialVersionUID = 3455814385213040058L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "分中心")
    @ApiModelProperty(value = "分中心")
    private String fzx;

    @Excel(name = "团体ID")
    @ApiModelProperty(value = "团体ID")
    private String intId;

    @Excel(name = "客户单位名称")
    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @Excel(name = "订单名称")
    @ApiModelProperty(value = "订单名称")
    private String ddmc;

    @Excel(name = "体检类型", readConverterExp = "0=健康,1=职业,1=综合")
    @ApiModelProperty(value = "体检类型 0健康、1职业、2综合")
    private Integer tjlx;

    @Excel(name = "来检人数")
    @ApiModelProperty(value = "来检人数")
    private String ljrs;

    @Excel(name = "来检业绩")
    @ApiModelProperty(value = "来检业绩")
    private String total;

    @Excel(name = "销售经理")
    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @Excel(name = "订单号")
    @ApiModelProperty(value = "订单号")
    private String ddh;
}
