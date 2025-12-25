package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 当日团体非统收汇总 返回数据
 */
@Data
public class FeiTongPayVo implements Serializable {
    private static final long serialVersionUID = 8964492444106885160L;

    @ApiModelProperty(value = "收费日期")
    private Date sfrq;

    @ApiModelProperty(value = "单位id")
    private String dwid;

    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @ApiModelProperty(value = "金额")
    private String je;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;
}
