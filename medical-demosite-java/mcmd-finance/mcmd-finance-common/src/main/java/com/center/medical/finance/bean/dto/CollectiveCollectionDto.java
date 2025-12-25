package com.center.medical.finance.bean.dto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CollectiveCollectionDto implements Serializable{
    private String orgName;
    private String noteName;
    private BigDecimal paid;
    private String name;
    private BigDecimal intId;
    private String posKingdeeNumber;
    private String kingdeeSaleer;
    private String userName;
    private String fzx;
}
