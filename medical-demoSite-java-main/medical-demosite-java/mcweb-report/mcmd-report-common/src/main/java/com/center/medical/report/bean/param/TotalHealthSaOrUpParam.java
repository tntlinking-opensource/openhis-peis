package com.center.medical.report.bean.param;

import com.center.medical.report.bean.model.BgFormData;
import com.center.medical.report.bean.model.BgGriddata;
import com.center.medical.report.bean.model.Formdata;
import com.center.medical.report.bean.model.Griddata;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TotalHealthSaOrUpParam implements Serializable {

    private static final long serialVersionUID = -4105948587494476778L;


    @ApiModelProperty(value = "保存")
    private int type;

    @ApiModelProperty(value = "健康0,职业1")
    private int dh;

    @ApiModelProperty(value = "体检号")
    private String patientno;

    @ApiModelProperty(value = "保存更新数据")
    private BgFormData BgFormData;

    @ApiModelProperty(value = "保存表格数据")
    private List<BgGriddata> bgGriddata;

}
