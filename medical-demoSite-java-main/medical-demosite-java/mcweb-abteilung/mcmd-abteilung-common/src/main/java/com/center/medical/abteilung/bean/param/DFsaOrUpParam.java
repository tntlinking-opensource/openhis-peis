package com.center.medical.abteilung.bean.param;

import com.center.medical.abteilung.bean.dto.DFDataDto;
import com.center.medical.abteilung.bean.dto.DFFormdataDto;
import com.center.medical.abteilung.bean.dto.DFGriddataDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 一般检查-审核参数
 */
@Data
public class DFsaOrUpParam implements Serializable {
    private static final long serialVersionUID = 8439840150474107990L;

    @ApiModelProperty(value = "体检号")
    private String patientCode;

    @ApiModelProperty(value = "科室ID")
    private String ksId;

    @ApiModelProperty(value = "formdata")
    private DFFormdataDto formdata;

    @ApiModelProperty(value = "griddata")
    private List<DFGriddataDto> griddata;

    @ApiModelProperty(value = "data")
    private DFDataDto data;

}
