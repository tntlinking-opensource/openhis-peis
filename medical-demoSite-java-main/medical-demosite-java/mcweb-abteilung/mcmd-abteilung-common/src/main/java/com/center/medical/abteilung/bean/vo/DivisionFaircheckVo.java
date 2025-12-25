package com.center.medical.abteilung.bean.vo;

import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.bean.model.Tjreg;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class DivisionFaircheckVo implements Serializable {

    private static final long serialVersionUID = 1043948672835693891L;


    @ApiModelProperty(value = "uncharged")
    private String uncharged;

    @ApiModelProperty(value = "体检者表")
    private Peispatient patient;

    @ApiModelProperty(value = "照片")
    private String picture;

    @ApiModelProperty(value = "vip")
    private String isVIP;

    @ApiModelProperty(value = "体检者收费项目表")
    private Peispatientfeeitem feeitem;

    @ApiModelProperty(value = "科室检查结果主表")
    private SectionResultMain sectionResultMain;

    @ApiModelProperty(value = "一般检查表")
    private Tjreg tjreg;

}
