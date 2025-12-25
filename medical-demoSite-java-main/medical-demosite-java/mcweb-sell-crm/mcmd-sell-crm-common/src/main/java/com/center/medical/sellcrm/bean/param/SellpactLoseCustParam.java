package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SellpactLoseCustParam implements Serializable {

    private static final long serialVersionUID = -4813174643883314685L;


    @ApiModelProperty(value = "客户单位名称id(搜索条件)")
    private String khdwmcid;


    @ApiModelProperty(value = "年份")
    private String year;


    @ApiParam(hidden = true)
    @ApiModelProperty(value = "分中心id")
    private String fzxId;


    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;


    @ApiParam(hidden = true)
    @ApiModelProperty(value = "筛选时间")
    private Date sxDate;
}
