package com.center.medical.finance.bean.param;

import com.center.medical.finance.bean.dto.TCFormDataDto;
import com.center.medical.finance.bean.dto.TCGridDataDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 团体结算保存 参数
 */
@Data
public class TCSaOrUpParam implements Serializable {
    private static final long serialVersionUID = -2278990745505608727L;

    @ApiModelProperty(value = "上方数据")
    private TCFormDataDto formdata;


    @ApiModelProperty(value = "下方数据")
    private List<TCGridDataDto> griddata;
}
