package com.center.medical.abteilung.bean.vo;

import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.bean.model.Peispatient;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DivisionInspectionVo implements Serializable {


    private static final long serialVersionUID = -1158372948350070465L;

    @ApiModelProperty(value = "科室检查结果主表(main)")
    private SectionResultMain sectionResultMain;

    @ApiModelProperty(value = "体检者表(patient)")
    private Peispatient peispatient;


    @ApiModelProperty(value = "相片")
    private String picture;


    @ApiModelProperty(value = "vip")
    private String isVIP;

}
