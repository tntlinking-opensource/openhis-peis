package com.center.medical.abteilung.bean.param;

import com.center.medical.abteilung.bean.dto.PbFormdataDto;
import com.center.medical.abteilung.bean.dto.PbGriddataDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 快捷开药-保存 参数
 */
@Data
public class PrescribeParam implements Serializable {
    private static final long serialVersionUID = -2246558576706263894L;

    @ApiModelProperty(value = "保存更新数据")
    private PbFormdataDto formdata;

    @ApiModelProperty(value = "保存表格数据")
    private List<PbGriddataDto> griddata;

}
