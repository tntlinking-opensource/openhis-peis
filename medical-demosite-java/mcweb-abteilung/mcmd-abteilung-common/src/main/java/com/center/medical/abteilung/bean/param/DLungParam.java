package com.center.medical.abteilung.bean.param;

import com.center.medical.abteilung.bean.dto.DLDataDto;
import com.center.medical.abteilung.bean.dto.DLGriddataDto;
import com.center.medical.bean.model.Lung;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *  肺功能 审核 参数
 */
@Data
public class DLungParam implements Serializable {
    private static final long serialVersionUID = -3903719039834991033L;

    @ApiModelProperty(value = "肺功能审核数据")
    private Lung formdata;

    @ApiModelProperty(value = "肺功能结论词数据")
    private List<DLGriddataDto> griddata;

    @ApiModelProperty(value = "肺功能审核小结数据")
    private DLDataDto data;

    @ApiModelProperty(value = "体检号")
    private String patientCode;

    @ApiModelProperty(value = "科室id")
    private String ksId;
}
