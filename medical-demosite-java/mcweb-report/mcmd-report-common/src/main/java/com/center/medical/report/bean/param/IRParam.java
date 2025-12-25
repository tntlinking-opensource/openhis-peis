package com.center.medical.report.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 检验报告分页参数
 */
@Data
public class IRParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 2575974156418658646L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "打印状态")
    private Integer printStatus;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;
}
