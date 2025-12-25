package com.center.medical.center.qingdao.profession.entity.oracle;


import javax.persistence.*;
import java.util.Date;

/**
 * 体检者表
 * @ClassName: Peispatient
 * @Description: 体检者表
 * @author zhanghj
 * @date 2016年12月2日 下午2:28:45
 *由于归档功能使用触发器，因此不建议修改此表
 */
@Entity
@Table(name = "PEISPATIENT")
public class OrPeispatient {

    private String id;
    private String idCis;//线上档案id（
    private String idPatientarchive;// 档案ID
    private String patientcode;// 体检号
    private String patientarchiveno;// 档案号）
    private String patientcardno;// 一卡通号
    private String patientbizno;// 业务编号
    private String idcardno;// 身份证号
    private Integer numorgresv;// 订单号
    private String patientname;// 姓名
    private String inputCode;// 输入码
    private String idOrgreservationgroup;// 任务分组ID（2018-1-2 如果是复查，有团体任务id但是没有团体分组id，复查的人可以在同一个任务中出现多个相同的人）
    private String idOrgreservation;// 预定任务ID == 团体任务表主表id（PEISORGRESERVATION 表id）
    private String idOrg;// 团体ID
    private String orgName;// 团体名称
    private String orgDepart;// 团体部门
    private String idFeetype;//十周年卡号+团检专属卡卡号+活动专属卡卡号（团检专属卡可更换套餐，但这里记录的仍是卡赠送的那个套餐的id）
    private String idPayway;// 预支付方式ID
    private String payway;// 预支付方式
    private Double personpricelimit;// 统收限额-->原价(现用)
    private String idSex;// 性别ID
    private Date birthdate;// 出生日期
    private Integer age;// 年龄
    private String idMarriage;// 婚姻ID
    private String marriage;// 婚姻
    private String idNation;// 民族ID
    private String nation;// 民族
    private String address;// 地址
    private String idInformway;// 通知方式ID
    private String idOpendoctor;// 开单医生ID
    private String doctorapply;// 开单医生
    private String email;// 电子邮箱
    private String phone;// 电话
    private String idPatientclass;// 体检者类型ID PatienType表
    private String idResarea;// 地区ID   籍贯表
    private String resarea;// 地区
    private Integer FIsforprepare;// 预定  0：不是备单人员 1：备单人员
    private Integer FIsforreserve;// 预约 1预约
    private Integer FRegistered;// 已登记
    private Date dateregister;// 登记时间
    private Double moneyamount;// 应付金额 (存在并发问题，不准)
    private Double moneyamountpaid;// 实付金额(存在并发问题，不准)
    private String guidancenote;//导引单备注
    private String workno;// 工号
    private String idDoctorreg;// 登记员ID
    private String doctorreg;// 登记员（username）
    //当同时存在复查和补查时，体检类型是综合
    private String idExamtype;// 体检类型ID [{id:0,text:"健康体检"},{id:1,text:"职业体检"},{id:2,text:"综合"},3复查]
    private String examsuiteName;// 套餐名称
    private String examsuiteAlias;//家人手机号
    private String idDoctorapply;// 健康锁定人
    private Integer FGuidanceprinted;// 指引单已打次数
    private Integer FExamstarted;// 已开始体检
    private Integer FReadytofinal;// 分检完成
    private Date ReadytofinalDate;//分拣完成时间
    private String idDoctorfee;// 收费员ID
    private Integer FPaused;// 禁检
    private Integer FFinallocked;// 健康锁定
    private Integer FFinalexamed;// 总检完成
    private String idDoctorfinal;// 总检医生ID
    private String doctorfinalNameR;// 总检医生
    private Date datefinalexamed;// 总检时间
    private Integer FClosed;// 结案
    private Date dateclosed;// 结案日期
    private String note;// 前台自己用的备注
    private String knowledge;// 个性套餐问题答案 已@@分隔
    private Date timingstartedat;//备单体检号生成时间
    private String hospitalcode;// 分中心id
    private Integer idExamplace;//pacs登记页面已登记
    private String parsedassignedsuiteandfi;// 职业锁定人
    private String parsedassignedgroupandfi;//复查通知单word路径
    private String parsedsuiteandfi;//禁忌症告知书word路径
    private String parsedsuiteandfilab;//职业病告知书word路径
    private Integer idGuidenurse; // 职业锁定完成
    private String patientnameencoded;//职业总检医生
    private String patientcodehiden;//职业总检医生ID
    private Integer FWordprinted;//平安报告上传标志    1已上传   null未上传
    private String guidancenote2;//备单体检号生成人 + 补检的销售经理id
    private Integer useCodeHiden;// 团体与个人体检标志 0：个人 1：团体
    private Integer idPatientclass3;//确认来检  1是  null/0否
    private Date dateregisternotime;//职业总检时间
    private Integer counterreportprinted;//当前体检号是第几次复查（如果第一次来，直接改成复查，算是第一次复查，如果第一次下了复查结论，再来算第二次复查）
    private Date dateguidancereturned;// 预约时间
    private Integer FOutpatient;//平安平台已确认   1确认   null未确认
    private String patientnamereceipt;//平安订单ID
    private String patientnamepinyin;//十周年套餐id+团检专属卡套餐id+活动专属卡套餐id
    private String statusofhm;//平安   是否授权查看体检报告 Y N
    private String instancetag;//平安验证码
    private String inpatientno;// 复查体检号（第一次的体检号）
    private String insuranceno;//补检上一次（上一次体检号）
    private Integer countreportxml;//是否替检
    private Integer countreportcompare;//交单
    private Integer countreportoccupation;//1是平安平台预约的体检者
    private Integer countreportoccupationpdf;//复查通知单生成状态   0未生成1生成中2已生成
    private Integer countreportoccupationxml;//客户证件类型 身份证：1，护照：2，军人证：  6港澳通行证/回乡证或台胞证
    private String idTjtc;// 体检套餐ID
    private String jzdw;// 记账单位
    private String jzdwr;// 记账人
    private String spr;// 审批人
    private String tjr;// 原体检者姓名
    private String lqfs;// 领取方式
    private String yzbm;// 邮政编码
    private String yjaddress;// 邮寄地址
    private String qtxz;// 前台须知
    private String isHmdb;// 黑名单备注
    private Integer isHmd;// 黑名单
    private Integer isjj;// 是否加急
    private Integer zgl;// 总工龄
    private Integer jhgl;// 接害工龄
    private String jhys;// 接害因素
    private Integer jktjzt;// 健康体检状态 0:总检开始、1:总检完成、2:报告已打印、3:报告一审通过、4:报告一审不通过、5:二审通过、6:二审不通过、7:终审通过、8:终审不通过、9:报告已交接、10:报告已通知、11:报告已领取
    private Integer zytjzt;// 职业体检状态 0:总检开始、1:总检完成、2:报告已打印、3:报告一审通过、4:报告一审不通过、5:二审通过、6:二审不通过、7:终审通过、8:终审不通过、9:报告已交接、10:报告已通知、11:报告已领取
    private Integer tmyd;// 条码已打
    private Date medicaldate;// 预计体检时间
    private String trades;// 工种
    private Integer cjjgsfyhf;// 迟检是否已回访 0：未回访 1：已回访       删除
    private Integer bhgybsfyhf;// 不合格样本是否已回访 0：未回访 1：已回访 删除
    private Integer yxjgsfyhf;// 阳性结果是否已回访 0：未回访 1：已回访    删除
    private String medicaltype;// 体检类别 上岗前 在岗期间 离岗时(健康体检这个字段可能不是空，bug)
    private String prepayment;// 预付金额
    private Integer cultural;// 文化程度 0:小学 1:初中 2:技校 3:职高 4:高中 5:中专 6:大专 7:大学 8:研究生以上
    private Float tcprice;// 套餐价格[
    private Date workDate;// 参加工作时间
    private Date harmDate;// 接害时间
    private Integer FIsrecheck; // 是否需要复查 0: 未复查 1: 复查
    private Integer FSettlenone;// 中间库同步标识 0：登记、重新登记未同步 1：增加项目未同步 2：已同步
    private Integer diseasePrintNum;//职业报告打印次数 DISEASE_PRINT_NUM
    private Integer healthPrintNum;//健康报告打印次数 HEALTH_PRINT_NUM
    private Integer shortCode;// 短体检号
    private Integer guideSignleCount;// 导引单打印次数
    private Integer isNoticed;//1、预约已通知 0或NULL预约未通知
    private String reviewPdf;//复查通知单PDF
    private String contraindicatedPdf;//职业禁忌证PDF路径
    private String diseasePdf;//疑似职业病PDF路径
    private Double tsLimit;//统收限额  沃德小程序没用   海关小程序用
    private Date checkoutDate;//点击已结账的结账日期
    private Integer checkoutStatus;//结账状态 0或者Null：未结账 1：已结账
    private String worktypeId;//工种id  base_worktype
    private Integer idExamclass;//0健康类 1职业类 2综合类 5入职类 6疫苗类 7其他类
    private String originalTrade;//原始工种（名称,不需要是工种表里存在的）
    private Integer autoExamined;//收费项目自动已检
    private String thirdCode;//第三方条码  城阳健康证用到了

    @Id
    @Column(name = "ID", nullable = false, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "REVIEW_PDF")
    public String getReviewPdf() {
        return reviewPdf;
    }

    public void setReviewPdf(String reviewPdf) {
        this.reviewPdf = reviewPdf;
    }
    @Column(name = "CONTRAINDICATED_PDF")
    public String getContraindicatedPdf() {
        return contraindicatedPdf;
    }

    public void setContraindicatedPdf(String contraindicatedPdf) {
        this.contraindicatedPdf = contraindicatedPdf;
    }
    @Column(name = "DISEASE_PDF")
    public String getDiseasePdf() {
        return diseasePdf;
    }

    public void setDiseasePdf(String diseasePdf) {
        this.diseasePdf = diseasePdf;
    }
    public OrPeispatient() {
    }

    @Column(name = "DISEASE_PRINT_NUM")
    public Integer getDiseasePrintNum() {
        return diseasePrintNum;
    }

    public void setDiseasePrintNum(Integer diseasePrintNum) {
        this.diseasePrintNum = diseasePrintNum;
    }
    @Column(name = "HEALTH_PRINT_NUM")
    public Integer getHealthPrintNum() {
        return healthPrintNum;
    }

    public void setHealthPrintNum(Integer healthPrintNum) {
        this.healthPrintNum = healthPrintNum;
    }

    @Column(name = "ID_CIS", length = 50)
    public String getIdCis() {
        return this.idCis;
    }

    public void setIdCis(String idCis) {
        this.idCis = idCis;
    }

    @Column(name = "ID_PATIENTARCHIVE", length = 32)
    public String getIdPatientarchive() {
        return this.idPatientarchive;
    }

    public void setIdPatientarchive(String idPatientarchive) {
        this.idPatientarchive = idPatientarchive;
    }

    @Column(name = "PATIENTCODE", length = 50)
    public String getPatientcode() {
        return this.patientcode;
    }

    public void setPatientcode(String patientcode) {
        this.patientcode = patientcode;
    }

    @Column(name = "PATIENTARCHIVENO", length = 30)
    public String getPatientarchiveno() {
        return this.patientarchiveno;
    }

    public void setPatientarchiveno(String patientarchiveno) {
        this.patientarchiveno = patientarchiveno;
    }

    @Column(name = "PATIENTCARDNO", length = 30)
    public String getPatientcardno() {
        return this.patientcardno;
    }

    public void setPatientcardno(String patientcardno) {
        this.patientcardno = patientcardno;
    }

    @Column(name = "PATIENTBIZNO", length = 50)
    public String getPatientbizno() {
        return this.patientbizno;
    }

    public void setPatientbizno(String patientbizno) {
        this.patientbizno = patientbizno;
    }

    @Column(name = "IDCARDNO", length = 30)
    public String getIdcardno() {
        return this.idcardno;
    }

    public void setIdcardno(String idcardno) {
        this.idcardno = idcardno;
    }

    @Column(name = "NUMORGRESV", precision = 22, scale = 0)
    public Integer getNumorgresv() {
        return this.numorgresv;
    }

    public void setNumorgresv(Integer numorgresv) {
        this.numorgresv = numorgresv;
    }

    @Column(name = "PATIENTNAME", length = 50)
    public String getPatientname() {
        return this.patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    @Column(name = "INPUT_CODE", length = 25)
    public String getInputCode() {
        return this.inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    @Column(name = "ID_ORGRESERVATIONGROUP", length = 32)
    public String getIdOrgreservationgroup() {
        return this.idOrgreservationgroup;
    }

    public void setIdOrgreservationgroup(String idOrgreservationgroup) {
        this.idOrgreservationgroup = idOrgreservationgroup;
    }

    @Column(name = "ID_ORGRESERVATION", length = 32)
    public String getIdOrgreservation() {
        return this.idOrgreservation;
    }

    public void setIdOrgreservation(String idOrgreservation) {
        this.idOrgreservation = idOrgreservation;
    }

    @Column(name = "ID_ORG", length = 32)
    public String getIdOrg() {
        return this.idOrg;
    }

    public void setIdOrg(String idOrg) {
        this.idOrg = idOrg;
    }

    @Column(name = "ORG_NAME")
    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Column(name = "ORG_DEPART")
    public String getOrgDepart() {
        return this.orgDepart;
    }

    public void setOrgDepart(String orgDepart) {
        this.orgDepart = orgDepart;
    }

    @Column(name = "ID_FEETYPE", length = 32)
    public String getIdFeetype() {
        return this.idFeetype;
    }

    public void setIdFeetype(String idFeetype) {
        this.idFeetype = idFeetype;
    }

    @Column(name = "ID_PAYWAY", length = 2000)
    public String getIdPayway() {
        return this.idPayway;
    }

    public void setIdPayway(String idPayway) {
        this.idPayway = idPayway;
    }

    @Column(name = "PAYWAY", length = 4000)
    public String getPayway() {
        return this.payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }



    @Column(name = "PERSONPRICELIMIT", precision = 8, scale = 2)
    public Double getPersonpricelimit() {
        return this.personpricelimit;
    }

    public void setPersonpricelimit(Double personpricelimit) {
        this.personpricelimit = personpricelimit;
    }

    @Column(name = "ID_SEX", length = 32)
    public String getIdSex() {
        return this.idSex;
    }

    public void setIdSex(String idSex) {
        this.idSex = idSex;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTHDATE", length = 7)
    public Date getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Column(name = "AGE", precision = 4, scale = 1)
    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "ID_MARRIAGE", length = 32)
    public String getIdMarriage() {
        return this.idMarriage;
    }

    public void setIdMarriage(String idMarriage) {
        this.idMarriage = idMarriage;
    }

    @Column(name = "MARRIAGE", length = 4)
    public String getMarriage() {
        return this.marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    @Column(name = "ID_NATION", length = 32)
    public String getIdNation() {
        return this.idNation;
    }

    public void setIdNation(String idNation) {
        this.idNation = idNation;
    }

    @Column(name = "NATION", length = 16)
    public String getNation() {
        return this.nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @Column(name = "ADDRESS", length = 120)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "ID_INFORMWAY", length = 32)
    public String getIdInformway() {
        return this.idInformway;
    }

    public void setIdInformway(String idInformway) {
        this.idInformway = idInformway;
    }

    @Column(name = "ID_OPENDOCTOR", length = 32)
    public String getIdOpendoctor() {
        return this.idOpendoctor;
    }

    public void setIdOpendoctor(String idOpendoctor) {
        this.idOpendoctor = idOpendoctor;
    }

    @Column(name = "EMAIL", length = 80)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "PHONE", length = 30)
    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "ID_PATIENTCLASS", length = 32)
    public String getIdPatientclass() {
        return this.idPatientclass;
    }

    public void setIdPatientclass(String idPatientclass) {
        this.idPatientclass = idPatientclass;
    }

    @Column(name = "ID_RESAREA", length = 32)
    public String getIdResarea() {
        return this.idResarea;
    }

    public void setIdResarea(String idResarea) {
        this.idResarea = idResarea;
    }

    @Column(name = "RESAREA", length = 40)
    public String getResarea() {
        return this.resarea;
    }

    public void setResarea(String resarea) {
        this.resarea = resarea;
    }


    @Column(name = "F_ISFORPREPARE", precision = 1, scale = 0)
    public Integer getFIsforprepare() {
        return this.FIsforprepare;
    }

    public void setFIsforprepare(Integer FIsforprepare) {
        this.FIsforprepare = FIsforprepare;
    }

    @Column(name = "F_ISFORRESERVE", precision = 1, scale = 0)
    public Integer getFIsforreserve() {
        return this.FIsforreserve;
    }

    public void setFIsforreserve(Integer FIsforreserve) {
        this.FIsforreserve = FIsforreserve;
    }

    @Column(name = "F_REGISTERED", precision = 1, scale = 0)
    public Integer getFRegistered() {
        return this.FRegistered;
    }

    public void setFRegistered(Integer FRegistered) {
        this.FRegistered = FRegistered;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATEREGISTER")
    public Date getDateregister() {
        return this.dateregister;
    }

    public void setDateregister(Date dateregister) {
        this.dateregister = dateregister;
    }

    @Column(name = "MONEYAMOUNT", precision = 8, scale =2)
    public Double getMoneyamount() {
        return this.moneyamount;
    }

    public void setMoneyamount(Double moneyamount) {
        this.moneyamount = moneyamount;
    }

    @Column(name = "MONEYAMOUNTPAID", precision = 8, scale =2)
    public Double getMoneyamountpaid() {
        return this.moneyamountpaid;
    }

    public void setMoneyamountpaid(Double moneyamountpaid) {
        this.moneyamountpaid = moneyamountpaid;
    }

    @Column(name = "GUIDANCENOTE", length = 200)
    public String getGuidancenote() {
        return this.guidancenote;
    }

    public void setGuidancenote(String guidancenote) {
        this.guidancenote = guidancenote;
    }

    @Column(name = "WORKNO", length = 20)
    public String getWorkno() {
        return this.workno;
    }

    public void setWorkno(String workno) {
        this.workno = workno;
    }

    @Column(name = "ID_DOCTORREG", length = 32)
    public String getIdDoctorreg() {
        return this.idDoctorreg;
    }

    public void setIdDoctorreg(String idDoctorreg) {
        this.idDoctorreg = idDoctorreg;
    }

    @Column(name = "DOCTORREG", length = 16)
    public String getDoctorreg() {
        return this.doctorreg;
    }

    public void setDoctorreg(String doctorreg) {
        this.doctorreg = doctorreg;
    }

    @Column(name = "ID_EXAMTYPE", length = 32)
    public String getIdExamtype() {
        return this.idExamtype;
    }

    public void setIdExamtype(String idExamtype) {
        this.idExamtype = idExamtype;
    }

    @Column(name = "EXAMSUITE_NAME", length = 200)
    public String getExamsuiteName() {
        return this.examsuiteName;
    }

    public void setExamsuiteName(String examsuiteName) {
        this.examsuiteName = examsuiteName;
    }

    @Column(name = "EXAMSUITE_ALIAS", length = 100)
    public String getExamsuiteAlias() {
        return this.examsuiteAlias;
    }

    public void setExamsuiteAlias(String examsuiteAlias) {
        this.examsuiteAlias = examsuiteAlias;
    }

    @Column(name = "ID_DOCTORAPPLY", length = 32)
    public String getIdDoctorapply() {
        return this.idDoctorapply;
    }

    public void setIdDoctorapply(String idDoctorapply) {
        this.idDoctorapply = idDoctorapply;
    }

    @Column(name = "DOCTORAPPLY", length = 16)
    public String getDoctorapply() {
        return this.doctorapply;
    }

    public void setDoctorapply(String doctorapply) {
        this.doctorapply = doctorapply;
    }

    @Column(name = "F_GUIDANCEPRINTED", precision = 1, scale = 0)
    public Integer getFGuidanceprinted() {
        return this.FGuidanceprinted;
    }

    public void setFGuidanceprinted(Integer FGuidanceprinted) {
        this.FGuidanceprinted = FGuidanceprinted;
    }

    @Column(name = "F_EXAMSTARTED", precision = 1, scale = 0)
    public Integer getFExamstarted() {
        return this.FExamstarted;
    }

    public void setFExamstarted(Integer FExamstarted) {
        this.FExamstarted = FExamstarted;
    }

    @Column(name = "F_READYTOFINAL", precision = 1, scale = 0)
    public Integer getFReadytofinal() {
        return this.FReadytofinal;
    }

    public void setFReadytofinal(Integer FReadytofinal) {
        this.FReadytofinal = FReadytofinal;
    }

    @Column(name = "ID_DOCTORFEE", length = 32)
    public String getIdDoctorfee() {
        return this.idDoctorfee;
    }

    public void setIdDoctorfee(String idDoctorfee) {
        this.idDoctorfee = idDoctorfee;
    }

    @Column(name = "F_PAUSED", precision = 1, scale = 0)
    public Integer getFPaused() {
        return this.FPaused;
    }

    public void setFPaused(Integer FPaused) {
        this.FPaused = FPaused;
    }

    @Column(name = "F_FINALLOCKED", precision = 1, scale = 0)
    public Integer getFFinallocked() {
        return this.FFinallocked;
    }

    public void setFFinallocked(Integer FFinallocked) {
        this.FFinallocked = FFinallocked;
    }

    @Column(name = "F_FINALEXAMED", precision = 1, scale = 0)
    public Integer getFFinalexamed() {
        return this.FFinalexamed;
    }

    public void setFFinalexamed(Integer FFinalexamed) {
        this.FFinalexamed = FFinalexamed;
    }

    @Column(name = "ID_DOCTORFINAL", length=32)
    public String getIdDoctorfinal() {
        return this.idDoctorfinal;
    }

    public void setIdDoctorfinal(String idDoctorfinal) {
        this.idDoctorfinal = idDoctorfinal;
    }

    @Column(name = "DOCTORFINAL_NAME_R", length = 12)
    public String getDoctorfinalNameR() {
        return this.doctorfinalNameR;
    }

    public void setDoctorfinalNameR(String doctorfinalNameR) {
        this.doctorfinalNameR = doctorfinalNameR;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATEFINALEXAMED", length = 7)
    public Date getDatefinalexamed() {
        return this.datefinalexamed;
    }

    public void setDatefinalexamed(Date datefinalexamed) {
        this.datefinalexamed = datefinalexamed;
    }


    @Column(name = "F_CLOSED", precision = 1, scale = 0)
    public Integer getFClosed() {
        return this.FClosed;
    }

    public void setFClosed(Integer FClosed) {
        this.FClosed = FClosed;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATECLOSED", length = 7)
    public Date getDateclosed() {
        return this.dateclosed;
    }

    public void setDateclosed(Date dateclosed) {
        this.dateclosed = dateclosed;
    }




    @Column(name = "NOTE", length = 1000)
    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }



    @Column(name = "KNOWLEDGE", length = 200)
    public String getKnowledge() {
        return this.knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }




    @Temporal(TemporalType.DATE)
    @Column(name = "TIMINGSTARTEDAT", length = 7)
    public Date getTimingstartedat() {
        return this.timingstartedat;
    }

    public void setTimingstartedat(Date timingstartedat) {
        this.timingstartedat = timingstartedat;
    }

    @Column(name = "HOSPITALCODE", length = 50)
    public String getHospitalcode() {
        return this.hospitalcode;
    }

    public void setHospitalcode(String hospitalcode) {
        this.hospitalcode = hospitalcode;
    }


    @Column(name = "ID_EXAMPLACE", precision = 22, scale = 0)
    public Integer getIdExamplace() {
        return this.idExamplace;
    }

    public void setIdExamplace(Integer idExamplace) {
        this.idExamplace = idExamplace;
    }

    @Column(name = "PARSEDASSIGNEDSUITEANDFI", length = 300)
    public String getParsedassignedsuiteandfi() {
        return this.parsedassignedsuiteandfi;
    }

    public void setParsedassignedsuiteandfi(String parsedassignedsuiteandfi) {
        this.parsedassignedsuiteandfi = parsedassignedsuiteandfi;
    }

    @Column(name = "PARSEDASSIGNEDGROUPANDFI", length = 300)
    public String getParsedassignedgroupandfi() {
        return this.parsedassignedgroupandfi;
    }

    public void setParsedassignedgroupandfi(String parsedassignedgroupandfi) {
        this.parsedassignedgroupandfi = parsedassignedgroupandfi;
    }

    @Column(name = "PARSEDSUITEANDFI", length = 300)
    public String getParsedsuiteandfi() {
        return this.parsedsuiteandfi;
    }

    public void setParsedsuiteandfi(String parsedsuiteandfi) {
        this.parsedsuiteandfi = parsedsuiteandfi;
    }

    @Column(name = "PARSEDSUITEANDFILAB", length = 300)
    public String getParsedsuiteandfilab() {
        return this.parsedsuiteandfilab;
    }

    public void setParsedsuiteandfilab(String parsedsuiteandfilab) {
        this.parsedsuiteandfilab = parsedsuiteandfilab;
    }

    @Column(name = "ID_GUIDENURSE", precision = 22, scale = 0)
    public Integer getIdGuidenurse() {
        return this.idGuidenurse;
    }

    public void setIdGuidenurse(Integer idGuidenurse) {
        this.idGuidenurse = idGuidenurse;
    }

    @Column(name = "PATIENTNAMEENCODED", length = 100)
    public String getPatientnameencoded() {
        return this.patientnameencoded;
    }

    public void setPatientnameencoded(String patientnameencoded) {
        this.patientnameencoded = patientnameencoded;
    }

    @Column(name = "PATIENTCODEHIDEN", length = 50)
    public String getPatientcodehiden() {
        return this.patientcodehiden;
    }

    public void setPatientcodehiden(String patientcodehiden) {
        this.patientcodehiden = patientcodehiden;
    }

    @Column(name = "F_WORDPRINTED", precision = 1, scale = 0)
    public Integer getFWordprinted() {
        return this.FWordprinted;
    }

    public void setFWordprinted(Integer FWordprinted) {
        this.FWordprinted = FWordprinted;
    }

    @Column(name = "GUIDANCENOTE2", length = 200)
    public String getGuidancenote2() {
        return this.guidancenote2;
    }

    public void setGuidancenote2(String guidancenote2) {
        this.guidancenote2 = guidancenote2;
    }

    @Column(name = "F_USECODEHIDEN", precision = 1, scale = 0)
    public Integer getUseCodeHiden() {
        return useCodeHiden;
    }

    public void setUseCodeHiden(Integer useCodeHiden) {
        this.useCodeHiden = useCodeHiden;
    }



    @Column(name = "ID_PATIENTCLASS3", precision = 22, scale = 0)
    public Integer getIdPatientclass3() {
        return this.idPatientclass3;
    }

    public void setIdPatientclass3(Integer idPatientclass3) {
        this.idPatientclass3 = idPatientclass3;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATEREGISTERNOTIME", length = 7)
    public Date getDateregisternotime() {
        return this.dateregisternotime;
    }

    public void setDateregisternotime(Date dateregisternotime) {
        this.dateregisternotime = dateregisternotime;
    }

    @Column(name = "COUNTERREPORTPRINTED", precision = 22, scale = 0)
    public Integer getCounterreportprinted() {
        return this.counterreportprinted;
    }

    public void setCounterreportprinted(Integer counterreportprinted) {
        this.counterreportprinted = counterreportprinted;
    }


    @Column(name = "F_ISRECHECK", precision = 1, scale = 0)
    public Integer getFIsrecheck() {
        return this.FIsrecheck;
    }

    public void setFIsrecheck(Integer FIsrecheck) {
        this.FIsrecheck = FIsrecheck;
    }

    @Column(name = "F_SETTLENONE", precision = 1, scale = 0)
    public Integer getFSettlenone() {
        return this.FSettlenone;
    }

    public void setFSettlenone(Integer FSettlenone) {
        this.FSettlenone = FSettlenone;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATEGUIDANCERETURNED", length = 7)
    public Date getDateguidancereturned() {
        return this.dateguidancereturned;
    }

    public void setDateguidancereturned(Date dateguidancereturned) {
        this.dateguidancereturned = dateguidancereturned;
    }



    @Column(name = "F_OUTPATIENT", precision = 1, scale = 0)
    public Integer getFOutpatient() {
        return this.FOutpatient;
    }

    public void setFOutpatient(Integer FOutpatient) {
        this.FOutpatient = FOutpatient;
    }

    @Column(name = "PATIENTNAMERECEIPT", length = 50)
    public String getPatientnamereceipt() {
        return this.patientnamereceipt;
    }

    public void setPatientnamereceipt(String patientnamereceipt) {
        this.patientnamereceipt = patientnamereceipt;
    }

    @Column(name = "PATIENTNAMEPINYIN", length = 50)
    public String getPatientnamepinyin() {
        return this.patientnamepinyin;
    }

    public void setPatientnamepinyin(String patientnamepinyin) {
        this.patientnamepinyin = patientnamepinyin;
    }

    @Column(name = "STATUSOFHM", length = 10)
    public String getStatusofhm() {
        return this.statusofhm;
    }

    public void setStatusofhm(String statusofhm) {
        this.statusofhm = statusofhm;
    }

    @Column(name = "INSTANCETAG", length = 30)
    public String getInstancetag() {
        return this.instancetag;
    }

    public void setInstancetag(String instancetag) {
        this.instancetag = instancetag;
    }


    @Column(name = "INPATIENTNO", length = 50)
    public String getInpatientno() {
        return this.inpatientno;
    }

    public void setInpatientno(String inpatientno) {
        this.inpatientno = inpatientno;
    }

    @Column(name = "INSURANCENO", length = 50)
    public String getInsuranceno() {
        return this.insuranceno;
    }

    public void setInsuranceno(String insuranceno) {
        this.insuranceno = insuranceno;
    }



    @Column(name = "COUNTREPORTXML", precision = 22, scale = 0)
    public Integer getCountreportxml() {
        return this.countreportxml;
    }

    public void setCountreportxml(Integer countreportxml) {
        this.countreportxml = countreportxml;
    }

    @Column(name = "COUNTREPORTCOMPARE", precision = 22, scale = 0)
    public Integer getCountreportcompare() {
        return this.countreportcompare;
    }

    public void setCountreportcompare(Integer countreportcompare) {
        this.countreportcompare = countreportcompare;
    }




    @Column(name = "COUNTREPORTOCCUPATION", precision = 22, scale = 0)
    public Integer getCountreportoccupation() {
        return this.countreportoccupation;
    }

    public void setCountreportoccupation(Integer countreportoccupation) {
        this.countreportoccupation = countreportoccupation;
    }

    @Column(name = "COUNTREPORTOCCUPATIONPDF", precision = 22, scale = 0)
    public Integer getCountreportoccupationpdf() {
        return this.countreportoccupationpdf;
    }

    public void
            setCountreportoccupationpdf(Integer countreportoccupationpdf) {
        this.countreportoccupationpdf = countreportoccupationpdf;
    }


    @Column(name = "COUNTREPORTOCCUPATIONXML", precision = 22, scale = 0)
    public Integer getCountreportoccupationxml() {
        return this.countreportoccupationxml;
    }

    public void
            setCountreportoccupationxml(Integer countreportoccupationxml) {
        this.countreportoccupationxml = countreportoccupationxml;
    }



    @Column(name = "ID_TJTC", length = 32)
    public String getIdTjtc() {
        return this.idTjtc;
    }

    public void setIdTjtc(String idTjtc) {
        this.idTjtc = idTjtc;
    }

    @Column(name = "JZDWR", length = 20)
    public String getJzdwr() {
        return this.jzdwr;
    }

    public void setJzdwr(String jzdwr) {
        this.jzdwr = jzdwr;
    }

    @Column(name = "SPR", length = 20)
    public String getSpr() {
        return this.spr;
    }

    public void setSpr(String spr) {
        this.spr = spr;
    }

    @Column(name = "TJR", length = 20)
    public String getTjr() {
        return this.tjr;
    }

    public void setTjr(String tjr) {
        this.tjr = tjr;
    }

    @Column(name = "LQFS", length = 10)
    public String getLqfs() {
        return this.lqfs;
    }

    public void setLqfs(String lqfs) {
        this.lqfs = lqfs;
    }

    @Column(name = "YZBM", precision = 6, scale = 0)
    public String getYzbm() {
        return this.yzbm;
    }

    public void setYzbm(String yzbm) {
        this.yzbm = yzbm;
    }

    @Column(name = "YJADDRESS", length = 200)
    public String getYjaddress() {
        return this.yjaddress;
    }

    public void setYjaddress(String yjaddress) {
        this.yjaddress = yjaddress;
    }

    @Column(name = "QTXZ", length = 2000)
    public String getQtxz() {
        return this.qtxz;
    }

    public void setQtxz(String qtxz) {
        this.qtxz = qtxz;
    }

    @Column(name = "IS_HMDB", length = 200)
    public String getIsHmdb() {
        return this.isHmdb;
    }

    public void setIsHmdb(String isHmdb) {
        this.isHmdb = isHmdb;
    }

    @Column(name = "IS_HMD", precision = 1, scale = 0)
    public Integer getIsHmd() {
        return this.isHmd;
    }

    public void setIsHmd(Integer isHmd) {
        this.isHmd = isHmd;
    }

    @Column(name = "ISJJ", precision = 1, scale = 0)
    public Integer getIsjj() {
        return this.isjj;
    }

    public void setIsjj(Integer isjj) {
        this.isjj = isjj;
    }

    @Column(name = "ZGL", precision = 4, scale = 0)
    public Integer getZgl() {
        return this.zgl;
    }

    public void setZgl(Integer zgl) {
        this.zgl = zgl;
    }

    @Column(name = "JHGL", precision = 4, scale = 0)
    public Integer getJhgl() {
        return this.jhgl;
    }

    public void setJhgl(Integer jhgl) {
        this.jhgl = jhgl;
    }

    @Column(name = "JHYS", length = 200)
    public String getJhys() {
        return this.jhys;
    }

    public void setJhys(String jhys) {
        this.jhys = jhys;
    }

    @Column(name = "JKTJZT", length = 2, scale = 0)
    public Integer getJktjzt() {
        return this.jktjzt;
    }

    public void setJktjzt(Integer jktjzt) {
        this.jktjzt = jktjzt;
    }

    @Column(name = "ZYTJZT", length = 2, scale = 0)
    public Integer getZytjzt() {
        return this.zytjzt;
    }

    public void setZytjzt(Integer zytjzt) {
        this.zytjzt = zytjzt;
    }

    @Column(name = "TMYD", precision = 8, scale = 0)
    public Integer getTmyd() {
        return this.tmyd;
    }

    public void setTmyd(Integer tmyd) {
        this.tmyd = tmyd;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "MEDICALDATE", length = 7)
    public Date getMedicaldate() {
        return this.medicaldate;
    }

    public void setMedicaldate(Date medicaldate) {
        this.medicaldate = medicaldate;
    }

    @Column(name = "TRADES", length = 50)
    public String getTrades() {
        return this.trades;
    }

    public void setTrades(String trades) {
        this.trades = trades;
    }

    @Column(name = "JZDW", length = 100)
    public String getJzdw() {
        return this.jzdw;
    }

    public void setJzdw(String jzdw) {
        this.jzdw = jzdw;
    }

    @Column(name = "CJJGSFYHF", precision = 1, scale = 0)
    public Integer getCjjgsfyhf() {
        return cjjgsfyhf;
    }

    public void setCjjgsfyhf(Integer cjjgsfyhf) {
        this.cjjgsfyhf = cjjgsfyhf;
    }

    @Column(name = "BHGYBSFYHF", precision = 1, scale = 0)
    public Integer getBhgybsfyhf() {
        return bhgybsfyhf;
    }

    public void setBhgybsfyhf(Integer bhgybsfyhf) {
        this.bhgybsfyhf = bhgybsfyhf;
    }

    @Column(name = "YXJGSFYHF", precision = 1, scale = 0)
    public Integer getYxjgsfyhf() {
        return yxjgsfyhf;
    }

    public void setYxjgsfyhf(Integer yxjgsfyhf) {
        this.yxjgsfyhf = yxjgsfyhf;
    }

    @Column(name = "MEDICALTYPE", length = 20)
    public String getMedicaltype() {
        return medicaltype;
    }

    public void setMedicaltype(String medicaltype) {
        this.medicaltype = medicaltype;
    }

    @Column(name = "PREPAYMENT", precision = 8, scale = 2)
    public String getPrepayment() {
        return prepayment;
    }

    public void setPrepayment(String prepayment) {
        this.prepayment = prepayment;
    }

    @Column(name = "CULTURAL", precision = 1, scale = 0)
    public Integer getCultural() {
        return cultural;
    }

    public void setCultural(Integer cultural) {
        this.cultural = cultural;
    } 

	@Column(name = "TCPRICE", precision = 8, scale = 2)
    public Float getTcprice() {
        return tcprice;
    }

    public void setTcprice(Float tcprice) {
        this.tcprice = tcprice;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "WORK_DATE", length = 7)
    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "HARM_DATE", length = 7)
    public Date getHarmDate() {
        return harmDate;
    }

    public void setHarmDate(Date harmDate) {
        this.harmDate = harmDate;
    }

	@Column(name="READYTOFINAL_DATE")
	public Date getReadytofinalDate() {
		return ReadytofinalDate;
	}

	public void setReadytofinalDate(Date readytofinalDate) {
		ReadytofinalDate = readytofinalDate;
	}

	@Column(name="SHORT_CODE", precision = 8, scale = 0)
    public Integer getShortCode() {
        return shortCode;
    }

    public void setShortCode(Integer shortCode) {
        this.shortCode = shortCode;
    }

    @Column(name= "GUIDE_SIGNLE_COUNT")
    public Integer getGuideSignleCount() {
        return guideSignleCount;
    }

    public void setGuideSignleCount(Integer guideSignleCount) {
        this.guideSignleCount = guideSignleCount;
    }

    @Column(name="IS_NOTICED")
	public Integer getIsNoticed() {
		return isNoticed;
	}

	public void setIsNoticed(Integer isNoticed) {
		this.isNoticed = isNoticed;
	}

	@Column(name="TS_LIMIT")
	public Double getTsLimit() {
		return tsLimit;
	}

	public void setTsLimit(Double tsLimit) {
		this.tsLimit = tsLimit;
	}
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CHECKOUT_DATE")
    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
      @Column(name = "CHECKOUT_STATUS")
    public Integer getCheckoutStatus() {
        return checkoutStatus;
    }

    public void setCheckoutStatus(Integer checkoutStatus) {
			this.checkoutStatus = checkoutStatus;
		}

    @Column(name = "WORKTYPE_ID")
    public String getWorktypeId() {
        return worktypeId;
    }

    public void setWorktypeId(String worktypeId) {
        this.worktypeId = worktypeId;
    }

    @Column(name = "ID_EXAMCLASS")
    public Integer getIdExamclass() {
        return idExamclass;
    }

    public void setIdExamclass(Integer idExamclass) {
        this.idExamclass = idExamclass;
    }


    @Column(name = "ORIGINAL_TRADE")
    public String getOriginalTrade() {
        return originalTrade;
    }

    public void setOriginalTrade(String originalTrade) {
        this.originalTrade = originalTrade;
    }

    @Column(name = "AUTO_EXAMINED")
    public Integer getAutoExamined() {
        return autoExamined;
    }

    public void setAutoExamined(Integer autoExamined) {
        this.autoExamined = autoExamined;
    }

    @Column(name = "THIRD_CODE")
    public String getThirdCode() {
        return thirdCode;
    }

    public void setThirdCode(String thirdCode) {
        this.thirdCode = thirdCode;
    }
}