package com.center.medical.report.bean.param;

import com.center.medical.report.bean.model.Formdata;
import com.center.medical.report.bean.model.Griddata;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FormdataAndGriddataParam implements Serializable {

    private static final long serialVersionUID = -7032562100145962807L;

    @ApiModelProperty(value = "保存表格数据")
    private List<Griddata> griddata;


    @ApiModelProperty(value = "保存更新数据")
    private Formdata formdata;
}
