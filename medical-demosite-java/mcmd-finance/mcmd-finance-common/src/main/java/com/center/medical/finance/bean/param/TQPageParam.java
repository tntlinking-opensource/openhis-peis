package com.center.medical.finance.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 记账管理-记账结算分页参数
 */
@Data
public class TQPageParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -2354315163485886678L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "客户单位id")
    private String khdwmcid;

    @ApiModelProperty(value = "记账单位")
    private String jzdw;

    @ApiModelProperty(value = "结算开始时间")
    private Date jsStartTime;

    @ApiModelProperty(value = "结算结束时间")
    private Date jsEndTime;

    @ApiModelProperty(value = "记账人")
    private String jzr;

    @ApiModelProperty(value = "结算 0未结算 1已结算")
    private Integer js;


}
