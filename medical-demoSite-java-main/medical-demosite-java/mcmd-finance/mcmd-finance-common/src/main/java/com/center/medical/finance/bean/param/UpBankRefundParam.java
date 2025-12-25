package com.center.medical.finance.bean.param;

import com.center.medical.finance.bean.dto.BRFormDataDto;
import com.center.medical.finance.bean.dto.BRGriddataDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 银行汇款结算-保存 参数
 */
@Data
public class UpBankRefundParam implements Serializable {
    private static final long serialVersionUID = 1076742049238907616L;


    @ApiModelProperty(value = "上方单位数据")
    private BRFormDataDto formdata;

    @ApiModelProperty(value = "下方表格数据")
    private List<BRGriddataDto> griddata;
}
