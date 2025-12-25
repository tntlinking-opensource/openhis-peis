package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.bean.enums.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * QT体检者表(Peispatient)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:09
 */
@Data
@TableName("md_peispatient")
@ApiModel(value = "Peispatient", description = "QT体检者表实体类")
public class Peispatient extends Model<Peispatient> implements Serializable {

    private static final long serialVersionUID = 499028088569178898L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "XID")
    private String id;

    @ApiModelProperty(value = "预定体检编码")
    private Integer idOrgpatient;

    @ApiModelProperty(value = "ID_CIS")
    private String idCis;

    @ApiModelProperty(value = "档案号")
    private String patientarchiveno;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "业务编号")
    private String patientbizno;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "任务分组ID")
    private String idOrgreservationgroup;

    @ApiModelProperty(value = "预定任务ID")
    private String idOrgreservation;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @ApiModelProperty(value = "是否有隐私项目：0.无 1.有")
    private String havePrivate;

    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;

    @ApiModelProperty(value = "支付方式")
    private String payway;

    @ApiModelProperty(value = "统收限额-->原价(现用)")
    private Double personpricelimit;

    @ApiModelProperty(value = "性别ID")
    private Integer idSex;

    @ApiModelProperty(value = "出生日期")
    private Date birthdate;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "婚姻状况：1.未婚 2.已婚 3.离异 4.丧偶 5.其他")
    private Integer idMarriage;

    @ApiModelProperty(value = "婚姻")
    private String marriage;

    @ApiModelProperty(value = "民族ID")
    private String idNation;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "通知方式ID")
    private String idInformway;

    @ApiModelProperty(value = "开单医生ID")
    private String idOpendoctor;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "体检者类型ID")
    private String idPatientclass;

    @ApiModelProperty(value = "地区ID")
    private String idResarea;

    @ApiModelProperty(value = "地区")
    private String resarea;

    @ApiModelProperty(value = "预定（已备单）")
    private Integer fIsforprepare;

    @ApiModelProperty(value = "是否预约：0.未预约 1.已预约")
    private Integer fIsforreserve;

    @ApiModelProperty(value = "是否登记：0.未登记 1.已登记")
    private Integer fRegistered;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "指引单说明（导引单备注）")
    private String guidancenote;

    @ApiModelProperty(value = "应付金额")
    private Double moneyamount;

    @ApiModelProperty(value = "实付金额")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "登记员ID")
    private String idDoctorreg;

    @ApiModelProperty(value = "登记员")
    private String doctorreg;

    /**
     * @see ExamType
     */
    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String idExamtype;

    @ApiModelProperty(value = "体检类型")
    private String idExamsuite;

    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;

    @ApiModelProperty(value = "家人手机号")
    private String examsuiteAlias;

    @ApiModelProperty(value = "开单医生ID2总检锁定人")
    private String idDoctorapply;

    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @ApiModelProperty(value = "指引单已打")
    private Integer fGuidanceprinted;

    @ApiModelProperty(value = "已开始体检")
    private Integer fExamstarted;

    @ApiModelProperty(value = "分检完成")
    private Integer fReadytofinal;

    @ApiModelProperty(value = "禁检")
    private Integer fPaused;

    @ApiModelProperty(value = "总检锁定")
    private Integer fFinallocked;

    @ApiModelProperty(value = "总检完成")
    private Double fFinalexamed;

    @ApiModelProperty(value = "总检医生ID")
    private String idDoctorfinal;

    @ApiModelProperty(value = "总检医生")
    private String doctorfinalNameR;

    @ApiModelProperty(value = "总检时间")
    private Date datefinalexamed;

    @ApiModelProperty(value = "结案")
    private Integer fClosed;

    @ApiModelProperty(value = "结案日期")
    private Date dateclosed;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "健康知识")
    private String knowledge;

    @ApiModelProperty(value = "体检者记时器开始")
    private Date timingstartedat;

    @ApiModelProperty(value = "医院代码")
    private String hospitalcode;

    @ApiModelProperty(value = "pacs登记页面已登记")
    private Double idExamplace;

    @ApiModelProperty(value = "职业锁定人")
    private String parsedassignedsuiteandfi;

    @ApiModelProperty(value = "复查通知单word路径")
    private String parsedassignedgroupandfi;

    @ApiModelProperty(value = "禁忌症告知书word路径")
    private String parsedsuiteandfi;

    @ApiModelProperty(value = "职业病告知书word路径")
    private String parsedsuiteandfilab;

    @ApiModelProperty(value = "职业锁定完成")
    private Double idGuidenurse;

    @ApiModelProperty(value = "职业总检医生")
    private String patientnameencoded;

    @ApiModelProperty(value = "职业总检医生ID")
    private String patientcodehiden;

    @ApiModelProperty(value = "平安报告上传标志")
    private Integer fWordprinted;

    @ApiModelProperty(value = "备单体检号生成人")
    private String guidancenote2;

    @ApiModelProperty(value = "个检/团检：0.个检 1.团检")
    private Integer fUsecodehiden;

    @ApiModelProperty(value = "确认来检")
    private Integer idPatientclass3;

    @ApiModelProperty(value = "职业总检时间")
    private Date dateregisternotime;

    @ApiModelProperty(value = "当前体检号是第几次复查")
    private Integer counterreportprinted;

    @ApiModelProperty(value = "是否复查：0.未复查 1. 复查")
    private Integer fIsrecheck;

    @ApiModelProperty(value = "中间库同步标识")
    private Integer fSettlenone;

    @ApiModelProperty(value = "预约时间")
    private Date dateguidancereturned;

    @ApiModelProperty(value = "体检者来源：关联sys_business_source表source_id")
    private Integer countreportoccupation;

    @ApiModelProperty(value = "第三方平台（平安、康淘等）确认：0.待确认 1.已确认")
    private Integer fOutpatient;

    @ApiModelProperty(value = "第三方平台（平安、康淘等）订单ID")
    private String patientnamereceipt;

    @ApiModelProperty(value = "十周年套餐id+团检专属卡套餐id+活动专属卡套餐id")
    private String patientnamepinyin;

    @ApiModelProperty(value = "是否授权第三方平台（平安、康淘等）查看体检报告：Y.是 N.否")
    private String statusofhm;

    @ApiModelProperty(value = "第三方平台（平安、康淘等）验证码")
    private String instancetag;

    @ApiModelProperty(value = "复查体检号（第一次的体检号）")
    private String inpatientno;

    @ApiModelProperty(value = "补检体检号（上一次体检号）")
    private String insuranceno;

    @ApiModelProperty(value = "是否替检：0.否 1.是")
    private Integer countreportxml;

    @ApiModelProperty(value = "是否交单：0.否 1.是")
    private Integer countreportcompare;

    @ApiModelProperty(value = "复查通知单生成状态：0未生成 1生成中 2已生成")
    private Integer countreportoccupationpdf;

    /**
     * @see CusCardType
     */
    @ApiModelProperty(value = "客户证件类型，详见：com.center.medical.bean.enums.CusCardType")
    private Integer countreportoccupationxml;

    @ApiModelProperty(value = "上传标示")
    private Integer scbs;

    @ApiModelProperty(value = "体检套餐ID")
    private String idTjtc;

    @ApiModelProperty(value = "记账单位")
    private String jzdw;

    @ApiModelProperty(value = "记账人")
    private String jzdwr;

    @ApiModelProperty(value = "审批人")
    private String spr;

    @ApiModelProperty(value = "替检人")
    private String tjr;

    @ApiModelProperty(value = "领取方式")
    private String lqfs;

    @ApiModelProperty(value = "邮政编码")
    private Integer yzbm;

    @ApiModelProperty(value = "邮寄地址")
    private String yjaddress;

    @ApiModelProperty(value = "前台须知")
    private String qtxz;

    @ApiModelProperty(value = "黑名单备注")
    private String isHmdb;

    @ApiModelProperty(value = "是否黑名单：0.否 1.是")
    private Integer isHmd;

    @ApiModelProperty(value = "是否加急：0.否 1.是")
    private Integer isjj;

    @ApiModelProperty(value = "总工龄")
    private Integer zgl;

    @ApiModelProperty(value = "接害工龄")
    private Integer jhgl;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    /**
     * @see Jktjzt
     */
    @ApiModelProperty(value = "健康总检状态：-1.总检未开始,0.总检开始、1.总检完成、2.报告已打印、3.报告一审通过、4.报告一审不通过、" +
            "5.二审通过、6.二审不通过、7.终审通过、8.终审不通过、9.报告已交接、10.报告已通知、11.报告已领取")
    private Integer jktjzt;

    /**
     * @see ExamStatus
     */
    @ApiModelProperty(value = "职业体检状态：0.总检开始 1.总检完成 2.报告已打印 3.报告一审通过 4.报告一审不通过 5.二审通过 " +
            "6.二审不通过 7.终审通过 8.终审不通过 9.报告已交接 10.报告已通知 11.报告已领取")
    private Integer zytjzt;

    @ApiModelProperty(value = "条码打印次数")
    private Integer tmyd;

    @ApiModelProperty(value = "体检时间")
    private Date medicaldate;

    @ApiModelProperty(value = "工种")
    private String trades;

    @ApiModelProperty(value = "迟检是否已回访：0:未回访 1：已回访")
    private Integer cjjgsfyhf;

    @ApiModelProperty(value = "不合格样本是否已回访：0:未回访 1：已回访")
    private Integer bhgybsfyhf;

    @ApiModelProperty(value = "阳性结果是否已回访：0:未回访 1：已回访")
    private Integer yxjgsfyhf;

    /**
     * @see MedicalType
     */
    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String medicaltype;

    @ApiModelProperty(value = "预付金额")
    private Double prepayment;

    @ApiModelProperty(value = "X套餐价格")
    private Double tcprice;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "文化水平，详见：com.center.medical.bean.enums.CulturalLevel")
    private Integer cultural;

    @ApiModelProperty(value = "工号")
    private String workno;

    @ApiModelProperty(value = "参加工作时间")
    private Date workDate;

    @ApiModelProperty(value = "从事该工种时间")
    private Date harmDate;

    @ApiModelProperty(value = "职业报告打印次数")
    private Integer diseasePrintNum;

    @ApiModelProperty(value = "健康报告打印次数")
    private Integer healthPrintNum;

    @ApiModelProperty(value = "分检完成时间")
    private Date readytofinalDate;

    @ApiModelProperty(value = "导引单打印次数")
    private Integer guideSignleCount;

    @ApiModelProperty(value = "短号体检号")
    private Integer shortCode;

    @ApiModelProperty(value = "是否预约通知：0或null.否 1.是")
    private Integer isNoticed;

    @ApiModelProperty(value = "复查PDF路径")
    private String reviewPdf;

    @ApiModelProperty(value = "职业禁忌证PDF路径")
    private String contraindicatedPdf;

    @ApiModelProperty(value = "疑似职业病PDF路径")
    private String diseasePdf;

    @ApiModelProperty(value = "统收限额")
    private Double tsLimit;

    @ApiModelProperty(value = "点击已结账的结账日期")
    private Date checkoutDate;

    @ApiModelProperty(value = "结账状态 0或者Null：未结账 1：已结账")
    private Integer checkoutStatus;

    @ApiModelProperty(value = "工种id  base_worktype")
    private String worktypeId;

    @ApiModelProperty(value = "检查类型，关联md_register_type表id")
    private Integer idExamclass;

    @ApiModelProperty(value = "原始工种（名称,不需要是工种表里存在的）")
    private String originalTrade;

    /**
     * @see PeispatientStatus
     */
    @ApiModelProperty(value = "体检状态，详见：com.center.medical.bean.enums.PeispatientStatus")
    private Integer status;

    @ApiModelProperty(value = "第三方条码  城阳健康证用到了")
    private String thirdCode;


    @ApiModelProperty(value = "SABC等级")
    private String sabc;

    @TableField(exist = false)
    @ApiModelProperty(value = "评价结果")
    private String appraiseResult;

    @TableField(exist = false)
    @ApiModelProperty(value = "发送方式ID")
    private String grantId;

    @TableField(exist = false)
    @ApiModelProperty(value = "发送方式名称")
    private String methodName;

    @TableField(exist = false)
    @ApiModelProperty(value = "isTotal")
    private String isTotal;

    @TableField(exist = false)
    @ApiModelProperty(value = "chestNum")
    private String chestNum;

    @TableField(exist = false)
    @ApiModelProperty(value = "开始登记时间")
    private Date beginTime;


    @TableField(exist = false)
    @ApiModelProperty(value = "结束登记时间")
    private Date endTime;


    @TableField(exist = false)
    @ApiModelProperty(value = "工种名称")
    private String workTypeName;


    @TableField(exist = false)
    @ApiModelProperty(value = "危害因素名字")
    private String harmNames;


    @TableField(exist = false)
    @ApiModelProperty(value = "体检类别中文")
    private String medicaltypeName;

    @TableField(exist = false)
    @ApiModelProperty(value = "分中心ids")
    private List<String> branchIds;
}
