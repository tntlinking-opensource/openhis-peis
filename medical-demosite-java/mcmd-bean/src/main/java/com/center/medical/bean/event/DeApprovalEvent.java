package com.center.medical.bean.event;

import com.center.medical.bean.model.Peispatientfeeitem;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 加项科室反审核(检验科除外) 参数
 */
@Data
@AllArgsConstructor
public class DeApprovalEvent {

    @ApiModelProperty(value = "消费信息")
    private Peispatientfeeitem param;



}
