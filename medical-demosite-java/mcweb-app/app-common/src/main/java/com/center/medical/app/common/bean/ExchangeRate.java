package com.center.medical.app.common.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 汇率： 本系统货币 和其他币种的兑换比例
 * @author yami
 */
@Data
public class ExchangeRate {

    /** 本系统 1 */
    private BigDecimal currencyLocal;

    /** 美元 */
    private BigDecimal currencyUsd;

    private BigDecimal zero = new BigDecimal("0.0");

    /** 获取美元的汇率 */
    public BigDecimal getUsdExchangeRate() {
        return div(this.currencyUsd,this.currencyLocal,4);
    }

    private BigDecimal div(BigDecimal v1, BigDecimal v2,int scale) {
        if (Objects.isNull(v1) || Objects.isNull(v2)) {
            return this.zero;
        }
        if (v1.compareTo(this.zero) <=0 || v2.compareTo(this.zero) <= 0) {
            return this.zero;
        }
        return v1.divide(v2,scale,BigDecimal.ROUND_HALF_UP);
    }

}
