package com.center.medical.query.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 报告信息查询 分页 参数
 */
@Data
public class ReportQueryParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 2578106477270242568L;

    @ApiModelProperty(value = "体检类型")
    private Integer tjlx;

    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "体检阶段：0.未开始总检 1.开始总检 2.总检完成")
    private Integer examstate;

    @ApiModelProperty(value = "预警类型")
    private Integer yjlx;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "当前时间减七天")
    private Date before7;
}
