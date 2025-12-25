package com.center.medical.workflow.bean.param;

import com.center.medical.workflow.bean.dto.SubmitForApprovalDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 提交审批参数
 */
@Data
public class SubmitForApprovalParam implements Serializable {
    private static final long serialVersionUID = -6041951109192561711L;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "订单名称")
    private String orderName;

    @ApiModelProperty(value = "体检者数据")
    private List<SubmitForApprovalDto> PatientList;


}
