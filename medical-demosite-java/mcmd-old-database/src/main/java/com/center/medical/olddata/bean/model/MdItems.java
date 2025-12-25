package com.center.medical.olddata.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * JC收费项目表(MdItems)表实体类
 *
 * @author ay
 * @since 2024-07-13 13:45:54
 */
@Data
@TableName("md_items")
@ApiModel(value = "MdItems", description = "JC收费项目表实体类")
public class MdItems extends Model<MdItems> implements Serializable {
    private static final long serialVersionUID = -78818987702115277L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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


    @ApiModelProperty(value = "部位ID")
    private String examfeeitemCodehm;


    @ApiModelProperty(value = "是否在APP出现：1.是  0或null.否")
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


    @ApiModelProperty(value = "条码打印分类id")
    private String examfeeitemClass;


    @ApiModelProperty(value = "报告项目ID")
    private Integer idReportitem;


    @ApiModelProperty(value = "创建人")
    private String xXxdm;


    @ApiModelProperty(value = "禁止打折字段，1禁止打折")
    private Integer fDiscountdisabled;


    @ApiModelProperty(value = "F_MAXOFFPERCENTLIMITED")
    private Integer fMaxoffpercentlimited;


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
    private Integer idGuidesheetgroup;


    @ApiModelProperty(value = "ID_GUIDESHEETGROUPSUB")
    private Integer idGuidesheetgroupsub;


    @ApiModelProperty(value = "GUIDESHEETGROUPSET")
    private String guidesheetgroupset;


    @ApiModelProperty(value = "GUIDESHEETGROUPSETDISPORDER")
    private String guidesheetgroupsetdisporder;


    @ApiModelProperty(value = "标本类型ID")
    private String idLabtype;


    @ApiModelProperty(value = "标本类型名称")
    private String labtypeR;


    @ApiModelProperty(value = "app分类ID")
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


    @ApiModelProperty(value = "图片路径")
    private String inputcodee;


    @ApiModelProperty(value = "独立报告")
    private Integer fReportalone;


    @ApiModelProperty(value = "男性项目")
    private Integer fMale;


    @ApiModelProperty(value = "女性项目")
    private Integer fFemale;


    @ApiModelProperty(value = "警示项目")
    private Integer fAlert;


    @ApiModelProperty(value = "警示信息")
    private String warningmsg;


    @ApiModelProperty(value = "自动VIP项目")
    private Integer fAutovip;


    @ApiModelProperty(value = "非检查项目")
    private Integer fNonexam;


    @ApiModelProperty(value = "是否药品")
    private Integer fIsdrug;


    @ApiModelProperty(value = "是否问卷")
    private Integer fIsforask;


    @ApiModelProperty(value = "指引单不打印")
    private Integer fHideinguidesheet;


    @ApiModelProperty(value = "早餐顺序")
    private String breakfastorder;


    @ApiModelProperty(value = "禁用")
    private Integer fDisabled;


    @ApiModelProperty(value = "F_INVISIBLE")
    private Integer fInvisible;


    @ApiModelProperty(value = "行序")
    private String disporder;


    @ApiModelProperty(value = "说明")
    private String note;


    @ApiModelProperty(value = "收费项目分类ID")
    private Integer idClassoffeeitem;


    @ApiModelProperty(value = "发票项目分类ID")
    private Integer idClassofreceipt;


    @ApiModelProperty(value = "ID_CLASSOFACCOUNT")
    private Integer idClassofaccount;


    @ApiModelProperty(value = "ID_FEEITEMCLASS2")
    private Integer idFeeitemclass2;


    @ApiModelProperty(value = "ID_FEEITEMCLASS3")
    private Integer idFeeitemclass3;


    @ApiModelProperty(value = "LOOKUPWARNING")
    private String lookupwarning;


    @ApiModelProperty(value = "必选项目ids")
    private String guidesheetcode;


    @ApiModelProperty(value = "LIMITEDINEXAMPLACEIDLIST")
    private String limitedinexamplaceidlist;


    @ApiModelProperty(value = "F_QTYSETTABLE")
    private Integer fQtysettable;


    @ApiModelProperty(value = "F_DOCTORBYFEEITEM")
    private Integer fDoctorbyfeeitem;


    @ApiModelProperty(value = "F_DOSPLITTING")
    private Integer fDosplitting;


    @ApiModelProperty(value = "SPECIFICATION")
    private String specification;


    @ApiModelProperty(value = "MEASUREUNIT")
    private String measureunit;


    @ApiModelProperty(value = "NUMPRICELISTNEEDED")
    private Long numpricelistneeded;


    @ApiModelProperty(value = "NUMPRICELISTINCOMPLETE")
    private Long numpricelistincomplete;


    @ApiModelProperty(value = "NUMUSEDFORDEVELOPER")
    private Long numusedfordeveloper;


    @ApiModelProperty(value = "F_DONTUPDATE")
    private Integer fDontupdate;


    @ApiModelProperty(value = "F_DONTUPDATEPRICEDETAIL")
    private Integer fDontupdatepricedetail;


    @ApiModelProperty(value = "F_COMPAREREPORT")
    private Integer fComparereport;


    @ApiModelProperty(value = "F_GUIDESHEETBACKUPSINGLEITEM")
    private Integer fGuidesheetbackupsingleitem;


    @ApiModelProperty(value = "上次统计检查次数时间（体检者收费项目创建时间）")
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


    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;


    @ApiModelProperty(value = "检查次数")
    private Integer jccs;


    @ApiModelProperty(value = "收费项目intID")
    private Integer examfeeitemid;


    @ApiModelProperty(value = "是否是公共的")
    private Integer isPublic;


    @ApiModelProperty(value = "分中心IDs，逗号拼接")
    private String fzxIds;


    @ApiModelProperty(value = "排检人数上限(废弃)")
    private Integer preUpperLimit;


    @ApiModelProperty(value = "是否禁用：1.是  0或null.否")
    private Integer isBan;


    @ApiModelProperty(value = "条码打印个数")
    private Integer barcodeCount;


    @ApiModelProperty(value = "条码打印名称")
    private String barcodeName;


    @ApiModelProperty(value = "序号，职业团检报告 五、检查项目 排序")
    private Integer groupOrder;


    @ApiModelProperty(value = "业务类型ID")
    private Long bsCatId;


    @ApiModelProperty(value = "标本种类代码，md_specimen_type表中的code")
    private String specimenTypeCode;

}
