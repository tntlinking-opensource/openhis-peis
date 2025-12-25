package com.center.medical.abteilung.bean.param;


import com.center.medical.abteilung.bean.model.JlcGridFaircheck;
import com.center.medical.abteilung.bean.model.Tjregs;
import com.center.medical.abteilung.bean.model.XjForm;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 一般检查
 */

@Data
public class DivisionFaircheckParam implements Serializable {

    private static final long serialVersionUID = -2065211083060784067L;

    @ApiModelProperty(value = "体检号")
    private String patientCode;

    @ApiModelProperty(value = "科室ID")
    private String ksId;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "是否补全，true是false否")
    private String autoFill;

    @ApiModelProperty(value = "tjreg")
    private Tjregs tjregs;

    @ApiModelProperty(value = "xjForm")
    private XjForm xjForm;

    @ApiModelProperty(value = "jlcGridFairchecks")
    private List<JlcGridFaircheck> jlcGridFairchecks;


}
