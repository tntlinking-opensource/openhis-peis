package com.center.medical.sellcrm.bean.param;

import com.center.medical.sellcrm.bean.dto.GpFormdataDto;
import com.center.medical.sellcrm.bean.dto.GpGriddataDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 体检团体分组保存参数
 */
@Data
public class SaOrUpGroupParam implements Serializable {
    private static final long serialVersionUID = 3334202861549398774L;

    @ApiModelProperty(value = "体检团体分组上方基本数据")
    private GpFormdataDto formdata;

    @ApiModelProperty(value = "体检团体分组保存左下表格数据")
    private List<GpGriddataDto> griddata;

}
