package com.center.medical.abteilung.bean.param;

import com.center.medical.abteilung.bean.dto.BmdJsonDataDto;
import com.center.medical.abteilung.bean.dto.DmdGriddata;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 骨密度审核参数
 */
@Data
public class DmbShenHeParam implements Serializable {

    private static final long serialVersionUID = -8988631287711135972L;


    @ApiModelProperty(value = "小结数据")
    private String xjdata;

    @ApiModelProperty(value = "检查人")
    private String jcr;

    @ApiModelProperty(value = "检查时间")
    private Date jcsj;

    @ApiModelProperty(value = "录入人")
    private String lrr;

    @ApiModelProperty(value = "录入时间")
    private Date lrsj;

    @ApiModelProperty(value = "体检号")
    private String patientCode;

    @ApiModelProperty(value = "科室id")
    private String ksID;

    @ApiModelProperty(value = "骨密度诊断-描述")
    private String gmdzd_ms;

    @ApiModelProperty(value = "T值-描述")
    private String tz_ms;

    @ApiModelProperty(value = "zz-描述")
    private String zz_ms;

    @ApiModelProperty(value = "jsondata")
    private List<BmdJsonDataDto> jsondata;

    @ApiModelProperty(value = "griddata")
    private List<DmdGriddata> griddata;

}
