package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * (PacsItems)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:46
 */
@Data
@TableName("PACS_ITEMS")
@ApiModel(value = "PacsItems", description = "$tableInfo.comment实体类")
public class PacsItems extends Model<PacsItems> implements Serializable {
    private static final long serialVersionUID = 541636409514359856L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String basexamltemId;

    @ApiModelProperty(value = "${column.comment}")
    private String sysmanmark;

    @ApiModelProperty(value = "${column.comment}")
    private String keyexamfeeitem;

    @ApiModelProperty(value = "${column.comment}")
    private String examfeeitemName;

    @ApiModelProperty(value = "${column.comment}")
    private String examfeeitemNameabr;

    @ApiModelProperty(value = "${column.comment}")
    private String examfeeitemNameprn;

    @ApiModelProperty(value = "${column.comment}")
    private String examfeeitemNameeng;

    @ApiModelProperty(value = "${column.comment}")
    private String examfeeitemNameengabr;

    @ApiModelProperty(value = "${column.comment}")
    private String examfeeitemCode;

    @ApiModelProperty(value = "${column.comment}")
    private String examfeeitemCode2;

    @ApiModelProperty(value = "${column.comment}")
    private String examfeeitemCode3;

    @ApiModelProperty(value = "${column.comment}")
    private String examfeeitemCodehm;

    @ApiModelProperty(value = "${column.comment}")
    private String examfeeitemCodex;

    @ApiModelProperty(value = "${column.comment}")
    private String examfeeitemFeecode;

    @ApiModelProperty(value = "${column.comment}")
    private String examfeeitemFeecodesub;

    @ApiModelProperty(value = "${column.comment}")
    private String examfeeitemPricecode;

    @ApiModelProperty(value = "${column.comment}")
    private String examfeeitemPricecodesub;

    @ApiModelProperty(value = "${column.comment}")
    private String examfeeitemHisname;

    @ApiModelProperty(value = "${column.comment}")
    private String examfeeitemClass;

    @ApiModelProperty(value = "${column.comment}")
    private Double idReportitem;

    @ApiModelProperty(value = "${column.comment}")
    private String xXxdm;

    @ApiModelProperty(value = "${column.comment}")
    private Double fDiscountdisabled;

    @ApiModelProperty(value = "${column.comment}")
    private Double fMaxoffpercentlimited;

    @ApiModelProperty(value = "${column.comment}")
    private Double maxoffpercent;

    @ApiModelProperty(value = "${column.comment}")
    private Double unitprice;

    @ApiModelProperty(value = "${column.comment}")
    private Double suiteprice;

    @ApiModelProperty(value = "${column.comment}")
    private Double specialprice;

    @ApiModelProperty(value = "${column.comment}")
    private Double foreignprice;

    @ApiModelProperty(value = "${column.comment}")
    private Double preferprice;

    @ApiModelProperty(value = "${column.comment}")
    private Double innerprice;

    @ApiModelProperty(value = "${column.comment}")
    private Double materialprice;

    @ApiModelProperty(value = "${column.comment}")
    private Double costprice;

    @ApiModelProperty(value = "${column.comment}")
    private Double coopprice;

    @ApiModelProperty(value = "${column.comment}")
    private String idCooporg;

    @ApiModelProperty(value = "${column.comment}")
    private Double exampoint;

    @ApiModelProperty(value = "${column.comment}")
    private String xDepartcode;

    @ApiModelProperty(value = "${column.comment}")
    private String idDepart;

    @ApiModelProperty(value = "${column.comment}")
    private String departName;

    @ApiModelProperty(value = "${column.comment}")
    private String xYbdm;

    @ApiModelProperty(value = "${column.comment}")
    private Double idGuidesheetgroup;

    @ApiModelProperty(value = "${column.comment}")
    private Double idGuidesheetgroupsub;

    @ApiModelProperty(value = "${column.comment}")
    private String guidesheetgroupset;

    @ApiModelProperty(value = "${column.comment}")
    private String guidesheetgroupsetdisporder;

    @ApiModelProperty(value = "${column.comment}")
    private Double idLabtype;

    @ApiModelProperty(value = "${column.comment}")
    private String labtypeR;

    @ApiModelProperty(value = "${column.comment}")
    private String labtypeSub;

    @ApiModelProperty(value = "${column.comment}")
    private String guidesheelabtypeset;

    @ApiModelProperty(value = "${column.comment}")
    private String guidesheelabtypesetdisporder;

    @ApiModelProperty(value = "${column.comment}")
    private String hisopendepartname;

    @ApiModelProperty(value = "${column.comment}")
    private String hisopendepartcode;

    @ApiModelProperty(value = "${column.comment}")
    private String hisexecdepartname;

    @ApiModelProperty(value = "${column.comment}")
    private String hisexecdepartcode;

    @ApiModelProperty(value = "${column.comment}")
    private String hisexecworkgroupname;

    @ApiModelProperty(value = "${column.comment}")
    private String hisexecworkgroupcode;

    @ApiModelProperty(value = "${column.comment}")
    private String hisexecworkstationname;

    @ApiModelProperty(value = "${column.comment}")
    private String hisexecworkstationcode;

    @ApiModelProperty(value = "${column.comment}")
    private String hisexecworkbenchname;

    @ApiModelProperty(value = "${column.comment}")
    private String hisexecworkbenchcode;

    @ApiModelProperty(value = "${column.comment}")
    private String inputCode;

    @ApiModelProperty(value = "${column.comment}")
    private String inputcodeb;

    @ApiModelProperty(value = "${column.comment}")
    private String inputcodec;

    @ApiModelProperty(value = "${column.comment}")
    private String inputcoded;

    @ApiModelProperty(value = "${column.comment}")
    private String inputcodee;

    @ApiModelProperty(value = "${column.comment}")
    private Double fReportalone;

    @ApiModelProperty(value = "${column.comment}")
    private Double fMale;

    @ApiModelProperty(value = "${column.comment}")
    private Double fFemale;

    @ApiModelProperty(value = "${column.comment}")
    private Double fAlert;

    @ApiModelProperty(value = "${column.comment}")
    private String warningmsg;

    @ApiModelProperty(value = "${column.comment}")
    private Double fAutovip;

    @ApiModelProperty(value = "${column.comment}")
    private Double fNonexam;

    @ApiModelProperty(value = "${column.comment}")
    private Double fIsdrug;

    @ApiModelProperty(value = "${column.comment}")
    private Double fIsforask;

    @ApiModelProperty(value = "${column.comment}")
    private Double fHideinguidesheet;

    @ApiModelProperty(value = "${column.comment}")
    private String breakfastorder;

    @ApiModelProperty(value = "${column.comment}")
    private Double fDisabled;

    @ApiModelProperty(value = "${column.comment}")
    private Double fInvisible;

    @ApiModelProperty(value = "${column.comment}")
    private String disporder;

    @ApiModelProperty(value = "${column.comment}")
    private String note;

    @ApiModelProperty(value = "${column.comment}")
    private Double idClassoffeeitem;

    @ApiModelProperty(value = "${column.comment}")
    private Double idClassofreceipt;

    @ApiModelProperty(value = "${column.comment}")
    private Double idClassofaccount;

    @ApiModelProperty(value = "${column.comment}")
    private Double idFeeitemclass2;

    @ApiModelProperty(value = "${column.comment}")
    private Double idFeeitemclass3;

    @ApiModelProperty(value = "${column.comment}")
    private String lookupwarning;

    @ApiModelProperty(value = "${column.comment}")
    private String guidesheetcode;

    @ApiModelProperty(value = "${column.comment}")
    private String limitedinexamplaceidlist;

    @ApiModelProperty(value = "${column.comment}")
    private Double fQtysettable;

    @ApiModelProperty(value = "${column.comment}")
    private Double fDoctorbyfeeitem;

    @ApiModelProperty(value = "${column.comment}")
    private Double fDosplitting;

    @ApiModelProperty(value = "${column.comment}")
    private String specification;

    @ApiModelProperty(value = "${column.comment}")
    private String measureunit;

    @ApiModelProperty(value = "${column.comment}")
    private Double numpricelistneeded;

    @ApiModelProperty(value = "${column.comment}")
    private Double numpricelistincomplete;

    @ApiModelProperty(value = "${column.comment}")
    private Double numusedfordeveloper;

    @ApiModelProperty(value = "${column.comment}")
    private Double fDontupdate;

    @ApiModelProperty(value = "${column.comment}")
    private Double fDontupdatepricedetail;

    @ApiModelProperty(value = "${column.comment}")
    private Double fComparereport;

    @ApiModelProperty(value = "${column.comment}")
    private Double fGuidesheetbackupsingleitem;

    @ApiModelProperty(value = "${column.comment}")
    private Date dtLastautoinsert;

    @ApiModelProperty(value = "${column.comment}")
    private Date dtLastautoupdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date dtLastautoupdatedetail;

    @ApiModelProperty(value = "${column.comment}")
    private String jz;

    @ApiModelProperty(value = "${column.comment}")
    private String sfxmsrm;

    @ApiModelProperty(value = "${column.comment}")
    private String xmdymc;

    @ApiModelProperty(value = "${column.comment}")
    private String dydfz;

    @ApiModelProperty(value = "${column.comment}")
    private String xh;

    @ApiModelProperty(value = "${column.comment}")
    private String dyddybs;

    @ApiModelProperty(value = "${column.comment}")
    private Double zk;

    @ApiModelProperty(value = "${column.comment}")
    private Double txjg;

    @ApiModelProperty(value = "${column.comment}")
    private Double wbjg;

    @ApiModelProperty(value = "${column.comment}")
    private Double ydjg;

    @ApiModelProperty(value = "${column.comment}")
    private Double nbj;

    @ApiModelProperty(value = "${column.comment}")
    private String zybm;

    @ApiModelProperty(value = "${column.comment}")
    private String yblx;

    @ApiModelProperty(value = "${column.comment}")
    private String dlbs;

    @ApiModelProperty(value = "${column.comment}")
    private String yblxmc;

    @ApiModelProperty(value = "${column.comment}")
    private String fylx;

    @ApiModelProperty(value = "${column.comment}")
    private String jcyy;

    @ApiModelProperty(value = "${column.comment}")
    private String cx;

    @ApiModelProperty(value = "${column.comment}")
    private String yblxid;

    @ApiModelProperty(value = "${column.comment}")
    private String tjlx;

    @ApiModelProperty(value = "${column.comment}")
    private String xb;

    @ApiModelProperty(value = "${column.comment}")
    private String bs;

    @ApiModelProperty(value = "${column.comment}")
    private String bz;

    @ApiModelProperty(value = "${column.comment}")
    private String bgdybs;

    @ApiModelProperty(value = "${column.comment}")
    private String xsdyfl;

    @ApiModelProperty(value = "${column.comment}")
    private Date ldrq;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String reviewMatters;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private String jccs;

    @ApiModelProperty(value = "${column.comment}")
    private String examfeeitemid;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isPublic;

    @ApiModelProperty(value = "${column.comment}")
    private String fzxIds;

    @ApiModelProperty(value = "${column.comment}")
    private String perUpperLimit;

    @ApiModelProperty(value = "${column.comment}")
    private String preUpperLimit;
}
