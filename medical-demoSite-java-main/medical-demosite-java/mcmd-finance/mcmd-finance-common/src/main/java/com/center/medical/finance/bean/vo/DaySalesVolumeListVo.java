package com.center.medical.finance.bean.vo;

import com.center.medical.finance.bean.dto.DSVOptionDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 当日线下业绩 数据
 */
@Data
public class DaySalesVolumeListVo implements Serializable {
    private static final long serialVersionUID = 7387907444995630264L;

    @ApiModelProperty(value = "总数据")
    private List<DaySalesVolumeVo> total;

    @ApiModelProperty(value = "选项数据")
    private List<List<DSVOptionDto>> Option;


}
