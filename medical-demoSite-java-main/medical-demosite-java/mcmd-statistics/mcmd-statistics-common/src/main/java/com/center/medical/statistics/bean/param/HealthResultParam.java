package com.center.medical.statistics.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 健康检查结果附表 分页参数
 */
@Data
public class HealthResultParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 1436069785559317843L;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "订单号")
    private String ddh;



    @ApiModelProperty(value = "报告开始时间")
    private Date reportStartTime;

    @ApiModelProperty(value = "报告结束时间")
    private Date reportEndTime;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检类型")
    private String idExamtype;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "销售经理id")
    private String xsjlid;
}
