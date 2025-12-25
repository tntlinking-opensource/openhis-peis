package com.center.medical.finance.bean.param;

import com.center.medical.finance.bean.dto.RCFormDataDto;
import com.center.medical.finance.bean.dto.RCGridDataDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 卡充值-保存充值参数
 */
@Data
public class RCSaOrUpParam implements Serializable {
    private static final long serialVersionUID = -5208314681233581059L;

    @ApiModelProperty(value = "新增充值保存左侧卡数据")
    private RCFormDataDto formdata;

    @ApiModelProperty(value = "新增充值保存左侧卡数据")
    private List<RCGridDataDto> griddata;

}
