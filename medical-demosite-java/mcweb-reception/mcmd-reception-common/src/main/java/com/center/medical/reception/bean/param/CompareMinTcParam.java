package com.center.medical.reception.bean.param;

import com.center.medical.reception.bean.dto.GIGriddataDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 团检退项匹配最小套餐参数
 */
@Data
public class CompareMinTcParam implements Serializable {
    private static final long serialVersionUID = -4265129282179242969L;

    @ApiModelProperty(value = "数据")
    private List<GIGriddataDto> griddata;

    @ApiModelProperty(value = "ids")
    private List<String> ids;

}
