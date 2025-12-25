package com.center.medical.finance.bean.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class EpayWaysDto implements Serializable {
    private String paywayName;
    private String paywayCompany;
    private String thingKingdeeNumber;
    private String groupKingdeeNumber;
    private String posKingdeeNumber;
    private String kingdeeSaleer;
    private BigDecimal tuan;
    private BigDecimal geren;
    private BigDecimal tuanTj;
    private BigDecimal gerenTj;
}
