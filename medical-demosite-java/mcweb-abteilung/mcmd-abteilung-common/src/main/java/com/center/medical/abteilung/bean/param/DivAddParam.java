package com.center.medical.abteilung.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 科室加项保存数据
 */
@Data
public class DivAddParam implements Serializable {
    private static final long serialVersionUID = 7991570859154086244L;

    @ApiModelProperty(value = "保存表格数据")
    private List<DivAddGriddata> griddata;

    @ApiModelProperty(value = "体检号")
    private String patientcode;
}
