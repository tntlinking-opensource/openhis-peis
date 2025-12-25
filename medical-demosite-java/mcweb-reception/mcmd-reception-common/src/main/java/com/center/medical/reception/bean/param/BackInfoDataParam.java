package com.center.medical.reception.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  收费日报-今日费用结算情况参数
 */
@Data
public class BackInfoDataParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 1870531519735613814L;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检号")
    private String patientcode;


}
