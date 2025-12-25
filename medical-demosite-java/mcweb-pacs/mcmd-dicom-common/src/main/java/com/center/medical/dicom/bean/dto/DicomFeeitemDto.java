package com.center.medical.dicom.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xhp
 * @since 2023-04-26 15:02
 */
@Data
public class DicomFeeitemDto {
    @ApiModelProperty(value = "体检者收费项目id")
    private String id;
    @ApiModelProperty(value = "科室编号")
    private String deptNo;
    @ApiModelProperty(value = "短号体检号")
    private Integer shortCode;
    @ApiModelProperty(value = "体检号")
    private String idPatient;
}
