package com.center.medical.reception.bean.param;

import com.center.medical.reception.bean.dto.ItemdataDto;
import com.center.medical.reception.bean.dto.RBGriddataDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 职业复查保存参数
 */
@Data
public class RBatchParam implements Serializable {
    private static final long serialVersionUID = 2739755985135967025L;

    @ApiModelProperty(value = "新增数据")
    private List<ItemdataDto> data;

    @ApiModelProperty(value = "保存表格数据")
    private List<RBGriddataDto> griddata;
}
