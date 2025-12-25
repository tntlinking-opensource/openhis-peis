package com.center.medical.pacs.bean.param;

import com.center.medical.pacs.bean.dto.PCFromDataDto;
import com.center.medical.pacs.bean.dto.PCGriddataDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * pacs彩超 保存参数
 */
@Data
public class PacsCsharpSaOrUpParam implements Serializable {
    private static final long serialVersionUID = -1141757747110078766L;

    @ApiModelProperty(value = "formdata参数")
    private PCFromDataDto formdata;

    @ApiModelProperty(value = "griddata参数")
    private List<PCGriddataDto> griddata;

    @ApiModelProperty(value = "用户名")
    private String username;
}
