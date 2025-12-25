package com.center.medical.workflow.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 保存预约会员类型的对象
 */
@Data
public class SubmitForApprovalDto implements Serializable {
    private static final long serialVersionUID = -7434224518245907611L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "金额应付")
    private Double moneyamount;

    @ApiModelProperty(value = "会员类型")
    private String idPatientclass;


}
