package com.center.medical.report.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class TotalAuditParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = 73388312733871871L;

    @ApiModelProperty(value = "审核人")
    private String name;
}
