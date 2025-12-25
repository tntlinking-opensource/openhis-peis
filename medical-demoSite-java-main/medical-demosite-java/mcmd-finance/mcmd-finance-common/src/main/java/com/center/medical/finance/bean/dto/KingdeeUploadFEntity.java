package com.center.medical.finance.bean.dto;

import lombok.Data;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

import java.io.Serializable;
import java.util.Date;

@Data
public class KingdeeUploadFEntity implements Serializable {

    private static final long serialVersionUID = -4056141476083580845L;

//    private int FEntryID;//实体主键
    private String F_RYKG_TJNO;//体检号
    private String FDATE;//体检日期
    private String F_RYKG_tjmc;//体检中心名称(branch表里的centerorgname字段)
    private String F_RYKG_BS;//团检和个检的标识，团检为1，个检为0
    private String F_RYKG_tjfmc;//单位名称，团检有公司名把公司名传过去，个检没公司名吧人名传过去
    private String F_RYKG_FYXM;//收费项目名称多个用逗号隔开
    private double FPriceQty;//单笔体检的人员数量,因为用体检号获取，所以这里都是1
    private double FPrice;//单价
    private double FALLAMOUNTFOR_D;//本次体检的金额,跟单价一样
    private double F_RYKG_zkl;//折扣率 原价/优惠价
    private String F_RYKG_ywy;//业务员
    private String F_RYKG_SKFSID;//收款方式id
    private String F_RYKG_skfs;//收款方式
    private Integer F_RYKG_IFJS;//是否结账 0否1是





//    private String Frecstatus;//结果
//    private Date Frectime;//日期
//    private String Fmessage;//消息


    public KingdeeUploadFEntity(String f_RYKG_TJNO, String FDATE, String f_RYKG_tjmc, String f_RYKG_BS, String f_RYKG_tjfmc, String f_RYKG_FYXM, double FPriceQty, double FPrice, double FALLAMOUNTFOR_D,double F_RYKG_zkl,String F_RYKG_ywy,String F_RYKG_SKFSID,String F_RYKG_skfs,Integer F_RYKG_IFJS) {
        F_RYKG_TJNO = f_RYKG_TJNO;
        this.FDATE = FDATE;
        F_RYKG_tjmc = f_RYKG_tjmc;
        F_RYKG_BS = f_RYKG_BS;
        F_RYKG_tjfmc = f_RYKG_tjfmc;
        F_RYKG_FYXM = f_RYKG_FYXM;
        this.FPriceQty = FPriceQty;
        this.FPrice = FPrice;
        this.FALLAMOUNTFOR_D = FALLAMOUNTFOR_D;
        this.F_RYKG_zkl = F_RYKG_zkl;
        this.F_RYKG_ywy = F_RYKG_ywy;
        this.F_RYKG_SKFSID = F_RYKG_SKFSID;
        this.F_RYKG_skfs = F_RYKG_skfs;
        this.F_RYKG_IFJS = F_RYKG_IFJS;
    }


    public KingdeeUploadFEntity() {
    }
}
