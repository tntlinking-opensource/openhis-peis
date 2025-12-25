package com.center.medical.abteilung.bean.param;

import com.center.medical.abteilung.bean.dto.SrFormdataDto;
import com.center.medical.abteilung.bean.dto.SrGriddataDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 新增外送机构
 */
@Data
public class SrSaOrUpParam implements Serializable {

    private static final long serialVersionUID = -5722735792626204679L;

    @ApiModelProperty(value = "保存更新数据")
    private SrFormdataDto formdata;

    @ApiModelProperty(value = "保存表格数据")
    private List<SrGriddataDto> griddata;
}
