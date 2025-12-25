package com.center.medical.abteilung.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 药房管理-审核发药分页参数
 */
@Data
public class DrugstorePreParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -2131741217785780420L;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "开药医生")
    private String username;

    @ApiModelProperty(value = "药品ID")
    private String drugId;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "状态")
    private String isFinished;
}
