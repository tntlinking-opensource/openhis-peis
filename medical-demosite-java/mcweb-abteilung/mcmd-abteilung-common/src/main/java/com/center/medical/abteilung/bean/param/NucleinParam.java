package com.center.medical.abteilung.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 路飞
 * @date: 2022-11-14 8:53
 * @description:
 */
@Data
public class NucleinParam extends BaseParam {

    @ApiModelProperty(value = "体检号")
    private String patientcode;
}
