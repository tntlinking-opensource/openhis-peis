package com.center.medical.statistics.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 职业健康检查结果结论附表 参数
 */
@Data
public class PositiveQueryParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -3407832220785130330L;

    @ApiModelProperty(value = "团体名称")
    private String idOrg;

    @ApiModelProperty(value = "医院代码")
    private String cid;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "当前体检号是第几次复查")
    private Integer counterreportprinted;

    @ApiModelProperty(value = "检查结论")
    private String occupationSummary;

    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String medicaltype;

    @ApiModelProperty(value = "开单销售")
    private String kdxs;


    @ApiModelProperty(value = "报告交接时间开始")
    private Date geStartTime;

    @ApiModelProperty(value = "报告交接时间结束")
    private Date geEndTime;

    @ApiModelProperty(value = "姓名")
    private String patientname;


    @ApiModelProperty(value = "销售经理")
    private String userName;

    @ApiModelProperty(value = "销售经理id")
    private String userNo;

    @ApiModelProperty(value = "部门")
    private String orgDepart;
}
