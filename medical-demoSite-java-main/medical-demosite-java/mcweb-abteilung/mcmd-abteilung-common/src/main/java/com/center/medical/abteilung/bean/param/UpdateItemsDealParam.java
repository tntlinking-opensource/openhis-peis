package com.center.medical.abteilung.bean.param;

import com.center.medical.abteilung.bean.dto.UIDDataDto;
import com.center.medical.abteilung.bean.dto.UIDGriddataDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 检完签到补检参数
 */
@Data
public class UpdateItemsDealParam implements Serializable {
    private static final long serialVersionUID = -4285347643020924038L;

    @ApiModelProperty(value = "体检号")
    private String patientCode;

    @ApiModelProperty(value = "保存表格数据")
    private List<UIDGriddataDto> griddata;

    @ApiModelProperty(value = "新增数据")
    private UIDDataDto data;
}
