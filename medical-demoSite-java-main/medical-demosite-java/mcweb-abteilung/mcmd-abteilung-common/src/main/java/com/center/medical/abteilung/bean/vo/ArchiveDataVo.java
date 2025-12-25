package com.center.medical.abteilung.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查看档案 体检者列表返回数据
 */
@Data
public class ArchiveDataVo implements Serializable {
    private static final long serialVersionUID = 4186534088073893668L;

    @ApiModelProperty(value = "体检日期")
    private String medicaldate;

    @ApiModelProperty(value = "档案号")
    private String patientarchiveno;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检者名称")
    private String patientname;

    @ApiModelProperty(value = "说明")
    private String note;
}
