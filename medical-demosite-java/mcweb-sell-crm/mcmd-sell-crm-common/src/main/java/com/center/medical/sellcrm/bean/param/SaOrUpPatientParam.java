package com.center.medical.sellcrm.bean.param;

import com.center.medical.sellcrm.bean.dto.GpFormdataDto;
import com.center.medical.sellcrm.bean.dto.PaGridDataDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 体检者基本信息保存（预登记）参数
 */
@Data
public class SaOrUpPatientParam implements Serializable {
    private static final long serialVersionUID = 5149824274170906700L;

    @ApiModelProperty(value = "分组id,预登记使用")
    private String id;

    @ApiModelProperty(value = "体检团体分组上方基本数据")
    private GpFormdataDto formdata;

    @ApiModelProperty(value = "体检团体分组右下体检者数据")
    private List<PaGridDataDto> griddata;
}
