package com.center.medical.sellcrm.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 复查单打印分页参数
 */
@Data
public class RecheckePrintParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 3548094629764684156L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "团体名称id")
    private String idOrg;
}
