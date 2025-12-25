package com.center.medical.pacslis.bean.param;

import com.center.medical.pacslis.bean.dto.GriddataDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 团检退项匹配最小套餐参数
 */
@Data
public class ComMinParam implements Serializable {

    private static final long serialVersionUID = -2999459571624174702L;

    @ApiModelProperty(value = "ids")
    private List<String> ids;

    @ApiModelProperty(value = "表格数据")
    private List<GriddataDto> griddata;
}
