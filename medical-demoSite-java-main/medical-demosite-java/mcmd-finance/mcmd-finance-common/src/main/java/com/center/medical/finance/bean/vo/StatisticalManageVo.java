package com.center.medical.finance.bean.vo;

import com.center.medical.finance.bean.dto.CustomerPriceDto;
import com.center.medical.finance.bean.dto.IncomeDto;
import com.center.medical.finance.bean.dto.KeyAccountDto;
import com.center.medical.finance.bean.dto.NewCustomersDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 财务报表-统计报表 返回数据
 */
@Data
public class StatisticalManageVo implements Serializable {
    private static final long serialVersionUID = -93342633756647254L;

    @ApiModelProperty(value = "新增客户及数量")
    private List<NewCustomersDto> newCustomers;

    @ApiModelProperty(value = "体检机构大客户")
    private List<KeyAccountDto> keyAccount;

    @ApiModelProperty(value = "体检收入")
    private List<IncomeDto> income;

    @ApiModelProperty(value = "客单价及折扣率")
    private List<CustomerPriceDto> customerPrice;

}
