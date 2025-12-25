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
 * JC收费项目表(Items)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:20
 */
@Data
@TableName("ITEMS")
@ApiModel(value = "Items", description = "JC收费项目表实体类")
public class Items extends Model<Items> implements Serializable {
    private static final long serialVersionUID = 251989542946062511L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "检查项目ID")
    private String basexamltemId;

    @ApiModelProperty(value = "系统维护标记")
    private String sysmanmark;

    @ApiModelProperty(value = "KEYEXAMFEEITEM")
    private String keyexamfeeitem;

    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "收费项目名称缩写")
    private String examfeeitemNameabr;

    @ApiModelProperty(value = "收费项目打印名称")
    private String examfeeitemNameprn;

    @ApiModelProperty(value = "收费项目英语名称")
    private String examfeeitemNameeng;

    @ApiModelProperty(value = "收费项目英语名称缩写")
    private String examfeeitemNameengabr;

    @ApiModelProperty(value = "收费项目代码")
    private String examfeeitemCode;

    @ApiModelProperty(value = "收费项目代码二")
    private String examfeeitemCode2;

    @ApiModelProperty(value = "EXAMFEEITEM_CODE3")
    private String examfeeitemCode3;

    @ApiModelProperty(value = "EXAMFEEITEM_CODEHM")
    private String examfeeitemCodehm;

    @ApiModelProperty(value = "EXAMFEEITEM_CODEX")
    private String examfeeitemCodex;

    @ApiModelProperty(value = "收费项目收费代码")
    private String examfeeitemFeecode;

    @ApiModelProperty(value = "EXAMFEEITEM_FEECODESUB")
    private String examfeeitemFeecodesub;

    @ApiModelProperty(value = "EXAMFEEITEM_PRICECODE")
    private String examfeeitemPricecode;

    @ApiModelProperty(value = "EXAMFEEITEM_PRICECODESUB")
    private String examfeeitemPricecodesub;

    @ApiModelProperty(value = "收费项目HIS名称")
    private String examfeeitemHisname;

    @ApiModelProperty(value = "EXAMFEEITEM_CLASS")
    private String examfeeitemClass;

    @ApiModelProperty(value = "报告项目ID")
    private Double idReportitem;

    @ApiModelProperty(value = "X_XXDM")
    private String xXxdm;

    @ApiModelProperty(value = "禁止打折标志")
    private Double fDiscountdisabled;

    @ApiModelProperty(value = "F_MAXOFFPERCENTLIMITED")
    private Double fMaxoffpercentlimited;

    @ApiModelProperty(value = "MAXOFFPERCENT")
    private Double maxoffpercent;

    @ApiModelProperty(value = "价格")
    private Double unitprice;

    @ApiModelProperty(value = "套餐价格")
    private Double suiteprice;

    @ApiModelProperty(value = "SPECIALPRICE")
    private Double specialprice;

    @ApiModelProperty(value = "FOREIGNPRICE")
    private Double foreignprice;

    @ApiModelProperty(value = "PREFERPRICE")
    private Double preferprice;

    @ApiModelProperty(value = "INNERPRICE")
    private Double innerprice;

    @ApiModelProperty(value = "耗材价格")
    private Double materialprice;

    @ApiModelProperty(value = "成本价")
    private Double costprice;

    @ApiModelProperty(value = "外部价")
    private Double coopprice;

    @ApiModelProperty(value = "外送机构ID")
    private String idCooporg;

    @ApiModelProperty(value = "EXAMPOINT")
    private Double exampoint;

    @ApiModelProperty(value = "X_DEPARTCODE")
    private String xDepartcode;

    @ApiModelProperty(value = "所属科室ID")
    private String idDepart;

    @ApiModelProperty(value = "所属科室名称")
    private String departName;

    @ApiModelProperty(value = "X_YBDM")
    private String xYbdm;

    @ApiModelProperty(value = "ID_GUIDESHEETGROUP")
    private Double idGuidesheetgroup;

    @ApiModelProperty(value = "ID_GUIDESHEETGROUPSUB")
    private Double idGuidesheetgroupsub;

    @ApiModelProperty(value = "GUIDESHEETGROUPSET")
    private String guidesheetgroupset;

    @ApiModelProperty(value = "GUIDESHEETGROUPSETDISPORDER")
    private String guidesheetgroupsetdisporder;

    @ApiModelProperty(value = "标本类型ID")
    private Double idLabtype;

    @ApiModelProperty(value = "标本类型名称")
    private String labtypeR;

    @ApiModelProperty(value = "LABTYPE_SUB")
    private String labtypeSub;

    @ApiModelProperty(value = "GUIDESHEELABTYPESET")
    private String guidesheelabtypeset;

    @ApiModelProperty(value = "GUIDESHEELABTYPESETDISPORDER")
    private String guidesheelabtypesetdisporder;

    @ApiModelProperty(value = "HISOPENDEPARTNAME")
    private String hisopendepartname;

    @ApiModelProperty(value = "HISOPENDEPARTCODE")
    private String hisopendepartcode;

    @ApiModelProperty(value = "HIS执行科室名称")
    private String hisexecdepartname;

    @ApiModelProperty(value = "HIS执行科室代码")
    private String hisexecdepartcode;

    @ApiModelProperty(value = "HIS执行工作组名称")
    private String hisexecworkgroupname;

    @ApiModelProperty(value = "HIS执行工作组代码")
    private String hisexecworkgroupcode;

    @ApiModelProperty(value = "HIS执行工作站名称")
    private String hisexecworkstationname;

    @ApiModelProperty(value = "HIS执行工作站代码")
    private String hisexecworkstationcode;

    @ApiModelProperty(value = "HIS执行工作台名称")
    private String hisexecworkbenchname;

    @ApiModelProperty(value = "HIS执行工作台代码")
    private String hisexecworkbenchcode;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "INPUTCODEB")
    private String inputcodeb;

    @ApiModelProperty(value = "INPUTCODEC")
    private String inputcodec;

    @ApiModelProperty(value = "INPUTCODED")
    private String inputcoded;

    @ApiModelProperty(value = "INPUTCODEE")
    private String inputcodee;

    @ApiModelProperty(value = "独立报告")
    private Double fReportalone;

    @ApiModelProperty(value = "男性项目")
    private Double fMale;

    @ApiModelProperty(value = "女性项目")
    private Double fFemale;

    @ApiModelProperty(value = "警示项目")
    private Double fAlert;

    @ApiModelProperty(value = "警示信息")
    private String warningmsg;

    @ApiModelProperty(value = "自动VIP项目")
    private Double fAutovip;

    @ApiModelProperty(value = "非检查项目")
    private Double fNonexam;

    @ApiModelProperty(value = "是否药品")
    private Double fIsdrug;

    @ApiModelProperty(value = "是否问卷")
    private Double fIsforask;

    @ApiModelProperty(value = "指引单不打印")
    private Double fHideinguidesheet;

    @ApiModelProperty(value = "早餐顺序")
    private String breakfastorder;

    @ApiModelProperty(value = "禁用")
    private Double fDisabled;

    @ApiModelProperty(value = "F_INVISIBLE")
    private Double fInvisible;

    @ApiModelProperty(value = "行序")
    private String disporder;

    @ApiModelProperty(value = "说明")
    private String note;

    @ApiModelProperty(value = "收费项目分类ID")
    private Double idClassoffeeitem;

    @ApiModelProperty(value = "发票项目分类ID")
    private Double idClassofreceipt;

    @ApiModelProperty(value = "ID_CLASSOFACCOUNT")
    private Double idClassofaccount;

    @ApiModelProperty(value = "ID_FEEITEMCLASS2")
    private Double idFeeitemclass2;

    @ApiModelProperty(value = "ID_FEEITEMCLASS3")
    private Double idFeeitemclass3;

    @ApiModelProperty(value = "LOOKUPWARNING")
    private String lookupwarning;

    @ApiModelProperty(value = "GUIDESHEETCODE")
    private String guidesheetcode;

    @ApiModelProperty(value = "LIMITEDINEXAMPLACEIDLIST")
    private String limitedinexamplaceidlist;

    @ApiModelProperty(value = "F_QTYSETTABLE")
    private Double fQtysettable;

    @ApiModelProperty(value = "F_DOCTORBYFEEITEM")
    private Double fDoctorbyfeeitem;

    @ApiModelProperty(value = "F_DOSPLITTING")
    private Double fDosplitting;

    @ApiModelProperty(value = "SPECIFICATION")
    private String specification;

    @ApiModelProperty(value = "MEASUREUNIT")
    private String measureunit;

    @ApiModelProperty(value = "NUMPRICELISTNEEDED")
    private Double numpricelistneeded;

    @ApiModelProperty(value = "NUMPRICELISTINCOMPLETE")
    private Double numpricelistincomplete;

    @ApiModelProperty(value = "NUMUSEDFORDEVELOPER")
    private Double numusedfordeveloper;

    @ApiModelProperty(value = "F_DONTUPDATE")
    private Double fDontupdate;

    @ApiModelProperty(value = "F_DONTUPDATEPRICEDETAIL")
    private Double fDontupdatepricedetail;

    @ApiModelProperty(value = "F_COMPAREREPORT")
    private Double fComparereport;

    @ApiModelProperty(value = "F_GUIDESHEETBACKUPSINGLEITEM")
    private Double fGuidesheetbackupsingleitem;

    @ApiModelProperty(value = "DT_LASTAUTOINSERT")
    private Date dtLastautoinsert;

    @ApiModelProperty(value = "DT_LASTAUTOUPDATE")
    private Date dtLastautoupdate;

    @ApiModelProperty(value = "DT_LASTAUTOUPDATEDETAIL")
    private Date dtLastautoupdatedetail;

    @ApiModelProperty(value = "健值")
    private String jz;

    @ApiModelProperty(value = "收费项目输入码")
    private String sfxmsrm;

    @ApiModelProperty(value = "项目打印名称")
    private String xmdymc;

    @ApiModelProperty(value = "导引单分组")
    private String dydfz;

    @ApiModelProperty(value = "序号")
    private String xh;

    @ApiModelProperty(value = "导引单打印标示")
    private String dyddybs;

    @ApiModelProperty(value = "折扣")
    private Double zk;

    @ApiModelProperty(value = "特需价格")
    private Double txjg;

    @ApiModelProperty(value = "外宾价格")
    private Double wbjg;

    @ApiModelProperty(value = "优待价格")
    private Double ydjg;

    @ApiModelProperty(value = "内部价")
    private Double nbj;

    @ApiModelProperty(value = "职业编码")
    private String zybm;

    @ApiModelProperty(value = "样本类型")
    private String yblx;

    @ApiModelProperty(value = "独立标示")
    private String dlbs;

    @ApiModelProperty(value = "样本类型名称")
    private String yblxmc;

    @ApiModelProperty(value = "费用类型")
    private String fylx;

    @ApiModelProperty(value = "检查意义")
    private String jcyy;

    @ApiModelProperty(value = "餐序")
    private String cx;

    @ApiModelProperty(value = "样本类型ID")
    private String yblxid;

    @ApiModelProperty(value = "体检类型")
    private String tjlx;

    @ApiModelProperty(value = "性别")
    private String xb;

    @ApiModelProperty(value = "标示")
    private String bs;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "报告打印标示")
    private String bgdybs;

    @ApiModelProperty(value = "销售打印分类")
    private String xsdyfl;

    @ApiModelProperty(value = "录单日期")
    private Date ldrq;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "复查注意事项")
    private String reviewMatters;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;

    @ApiModelProperty(value = "检查次数")
    private String jccs;

    @ApiModelProperty(value = "收费项目intID")
    private String examfeeitemid;

    @ApiModelProperty(value = "是否是公共的")
    private Integer isPublic;

    @ApiModelProperty(value = "分中心IDs，逗号拼接")
    private String fzxIds;

    @ApiModelProperty(value = "${column.comment}")
    private String preUpperLimit;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isBan;

    @ApiModelProperty(value = "${column.comment}")
    private Integer barcodeCount;

    @ApiModelProperty(value = "${column.comment}")
    private String barcodeName;

    @ApiModelProperty(value = "${column.comment}")
    private String groupOrder;
}
