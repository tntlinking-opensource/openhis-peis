package com.center.medical.bean.param;

import com.center.medical.bean.model.Peispatientcharge;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2023-03-16 17:39
 * @description: 费用预收提交参数
 */
@Data
public class AdvanceRefundParam implements Serializable {

    private static final long serialVersionUID = -3120055180473451836L;

    @ApiModelProperty(value = "体检号(ID)")
    private String patientcode;

    @ApiModelProperty(value = "体检者缴费记录列表")
    private List<Peispatientcharge> chargeItems;

}
