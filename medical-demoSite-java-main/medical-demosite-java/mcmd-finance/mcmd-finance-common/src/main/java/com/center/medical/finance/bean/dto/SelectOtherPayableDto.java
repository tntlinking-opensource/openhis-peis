package com.center.medical.finance.bean.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xhp
 * @since 2023-05-16 8:41
 */
@Data
public class SelectOtherPayableDto {

    private String idRemittanceway;

    private BigDecimal moneyamountpaid;

    private String idCustomer;

    private String accountNo;

    private String paywayName;

    private String isUpdate;
    //销售经理ID
    private String idFeecharger;
}
