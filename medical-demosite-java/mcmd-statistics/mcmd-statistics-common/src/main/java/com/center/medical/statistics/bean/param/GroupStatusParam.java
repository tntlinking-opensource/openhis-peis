package com.center.medical.statistics.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检状态明细 参数
 */
@Data
public class GroupStatusParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 8003074345467293428L;

    @ApiModelProperty(value = "登记开始时间")
    private Date startRegTime;

    @ApiModelProperty(value = "登记结束时间")
    private Date endRegTime;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "是否团检 0：个人 1：团检")
    private Integer isOrg;

    @ApiModelProperty(value = "开单医生id")
    private String idOpendoctor;

    @ApiModelProperty(value = "已登记")
    private Integer containUnchecked;


}
