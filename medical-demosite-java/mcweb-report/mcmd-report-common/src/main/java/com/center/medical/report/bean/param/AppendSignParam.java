package com.center.medical.report.bean.param;

import com.center.medical.report.bean.model.BgGriddata;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AppendSignParam implements Serializable {
    private static final long serialVersionUID = 6903593535281212845L;

    @ApiModelProperty(value = "体检号")
    private String patientno;

    @ApiModelProperty(value = "健康0 职业1，就是老系统的flag")
    private String dh;

    @ApiModelProperty(value = "保存表格数据")
    private List<BgGriddata> bgGriddata;
}
