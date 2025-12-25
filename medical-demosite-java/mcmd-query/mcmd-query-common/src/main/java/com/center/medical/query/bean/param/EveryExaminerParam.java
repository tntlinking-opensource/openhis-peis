package com.center.medical.query.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 每日体检明细 分页参数
 */
@Data
public class EveryExaminerParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -8926117443772335941L;

    @ApiModelProperty(value = "个检/团检：0.个检 1.团检")
    private Integer useCodeHiden;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "SABC级别")
    private String sabc;
    
    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "会员类型")
    private String idPatientclass;

    @ApiModelProperty(value = "开单医生ID")
    private String idOpendoctor;
}
