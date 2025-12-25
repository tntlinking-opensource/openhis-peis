package com.center.medical.finance.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售团检统计 查询右边列表 返回数据
 */
@Data
public class TotalListVo implements Serializable {
    private static final long serialVersionUID = -2105864885514498619L;

    @ApiModelProperty(value = "订单号")
    private String order;

    @ApiModelProperty(value = "备单日期")
    private Date regDate;

    @ApiModelProperty(value = "登记时间")
    private Date FirstDate;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "合计")
    private Double hj;

    @ApiModelProperty(value = "统收")
    private Double ts;

    @ApiModelProperty(value = "记账")
    private Double jz;


    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "其他")
    private Double qt;

}
