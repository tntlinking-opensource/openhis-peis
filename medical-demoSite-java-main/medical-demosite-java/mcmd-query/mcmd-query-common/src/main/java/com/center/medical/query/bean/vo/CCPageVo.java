package com.center.medical.query.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 自费收费汇总 分页 返回数据
 */
@Data
public class CCPageVo implements Serializable {
    private static final long serialVersionUID = 1413755997511535853L;


    @Excel(name = "收费方式")
    @ApiModelProperty(value = "付款方式")
    private String payway;

    @Excel(name = "收费员")
    @ApiModelProperty(value = "收费员")
    private String feecharger;

    @Excel(name = "金额")
    @ApiModelProperty(value = "结算金额")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "排序")
    private Integer seq;

}
