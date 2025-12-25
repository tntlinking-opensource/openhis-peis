package com.center.medical.abteilung.bean.param;

import com.center.medical.abteilung.bean.dto.LungGridData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 科室肺功能 保存结伦词 参数
 */
@Data
public class lungSaveJlcParam implements Serializable {
    private static final long serialVersionUID = -7912802418436498768L;

    @ApiModelProperty(value = "体检号")
    private String patientCode;

    @ApiModelProperty(value = "科室id")
    private String ksId;

    @ApiModelProperty(value = "结论词")
    private List<LungGridData> griddata;
}
