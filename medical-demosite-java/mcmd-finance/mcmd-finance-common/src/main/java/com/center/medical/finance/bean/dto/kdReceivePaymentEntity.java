package com.center.medical.finance.bean.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class kdReceivePaymentEntity implements Serializable {

    private static final long serialVersionUID = -4056141476083580845L;

//    private int FEntryID;//实体主键
    private String F_RYKG_SKDID;//收款单ID
    private String F_RYKG_SJKH;//客户
    private String F_RYKG_YG;//员工
    private String F_RYKG_SKJE;//金额
    private String F_RYKG_FYMC;//费用名称
    private String F_RYKG_ZHUZHI;//组织

    public kdReceivePaymentEntity(String f_RYKG_SKDID, String f_RYKG_SJKH, String f_RYKG_YG, String f_RYKG_SKJE, String f_RYKG_FYMC,String f_RYKG_ZHUZHI) {
        F_RYKG_SKDID = f_RYKG_SKDID;
        F_RYKG_SJKH = f_RYKG_SJKH;
        F_RYKG_YG = f_RYKG_YG;
        F_RYKG_SKJE = f_RYKG_SKJE;
        F_RYKG_FYMC = f_RYKG_FYMC;
        F_RYKG_ZHUZHI = f_RYKG_ZHUZHI;
    }

    public kdReceivePaymentEntity() {
    }
}
