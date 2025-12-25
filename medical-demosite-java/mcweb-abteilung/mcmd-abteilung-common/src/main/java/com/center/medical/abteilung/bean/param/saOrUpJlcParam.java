package com.center.medical.abteilung.bean.param;

import com.center.medical.abteilung.bean.dto.saOrUpJlcDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 结论词保存(图像检查)
 */
@Data
public class saOrUpJlcParam implements Serializable {
    private static final long serialVersionUID = 5456358042810885980L;


    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @ApiModelProperty(value = "科室id")
    private String ksID;

    @ApiModelProperty(value = "保存数据")
    private List<saOrUpJlcDto> griddata;
}
