package com.center.medical.reception.bean.dto;

import com.center.medical.bean.enums.CulturalLevel;
import com.center.medical.bean.enums.CusCardType;
import com.center.medical.bean.enums.ExamType;
import com.center.medical.bean.enums.MedicalType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2023-03-03 8:09
 * @description: 前台-登记管理-新增登记提交的登记信息
 */
@Data
@ApiModel(value = "登记信息", description = "前台-登记管理-新增登记提交的信息")
public class RegisterDto implements Serializable {

    private static final long serialVersionUID = 1904220814613806065L;

    @ApiModelProperty(value = "登记信息ID", position = 0)
    private String id;

    @ApiModelProperty(value = "分中心ID")
    private String hospitalcode;

    @ApiModelProperty(value = "订单号", position = 1)
    private String numorgresv;

    @ApiModelProperty(value = "订单号", position = 2)
    private String ddh;

    @ApiModelProperty(value = "团体ID", position = 3)
    private String idOrg;

    @ApiModelProperty(value = "团体名称", position = 4)
    private String orgName;

    @ApiModelProperty(value = "团体分组ID", position = 5)
    private String idOrgreservationgroup;

    @ApiModelProperty(value = "团体部门", position = 6)
    private String orgDepart;

    @ApiModelProperty(value = "体检套餐ID", position = 7)
    private String idTjtc;

    @ApiModelProperty(value = "开单医生ID", position = 8)
    private String idOpendoctor;

    @ApiModelProperty(value = "预约时间", position = 9)
    private Date dateguidancereturned;

    @ApiModelProperty(value = "头像图片", position = 10)
    private String picture;

    @ApiModelProperty(value = "是否预约：0.未预约 1.已预约", position = 11)
    private Integer fIsforreserve;

    @ApiModelProperty(value = "是否登记：0.未登记 1.已登记", position = 12)
    private Integer fRegistered;

    @ApiModelProperty(value = "登记时间", position = 13)
    private Date dateregister;

    @ApiModelProperty(value = "体检时间", position = 14)
    private Date medicaldate;

    @ApiModelProperty(value = "个检/团检：0.个检 1.团检", position = 15)
    private Integer fUsecodehiden;

    @ApiModelProperty(value = "体检号", position = 16)
    private String patientcode;

    @ApiModelProperty(value = "档案号", position = 17)
    private String patientarchiveno;

    @ApiModelProperty(value = "会员类型：调用用户等级列表接口，使用会员等级ID（levelId）", position = 18)
    private Integer idPatientclass;

    /**
     * @see ExamType
     */
    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查", position = 19)
    private String idExamtype;

    @ApiModelProperty(value = "姓名", position = 20)
    private String patientname;

    @ApiModelProperty(value = "备单分类，关联md_register_type表id", position = 21)
    private Integer idExamclass;

    @ApiModelProperty(value = "性别：0.男 1.女 2.通用", position = 22)
    private Integer idSex;

    @ApiModelProperty(value = "籍贯ID", position = 23)
    private String idResarea;

    @ApiModelProperty(value = "籍贯名称", position = 24)
    private String resarea;

    @ApiModelProperty(value = "民族ID", position = 25)
    private String idNation;

    @ApiModelProperty(value = "民族名称", position = 26)
    private String nation;

    @ApiModelProperty(value = "生日", position = 27)
    private Date birthdate;

    @ApiModelProperty(value = "婚姻状况：1.未婚 2.已婚 3.离异 4.丧偶 5.其他", position = 28)
    private Integer idMarriage;

    @ApiModelProperty(value = "电话", position = 29)
    private String phone;

    @ApiModelProperty(value = "家人手机号", position = 30)
    private String examsuiteAlias;

    /**
     * @see CusCardType
     */
    @ApiModelProperty(value = "证件类型：1.身份证 2.护照 6.军人证  7.港澳通行证/回乡证或台胞证", position = 31)
    private Integer countreportoccupationxml;

    @ApiModelProperty(value = "证件号", position = 32)
    private String idcardno;

    @ApiModelProperty(value = "年龄", position = 33)
    private Integer age;

    @ApiModelProperty(value = "地址", position = 34)
    private String address;

    @ApiModelProperty(value = "是否替检：0.否 1.是", position = 35)
    private Integer countreportxml;

    @ApiModelProperty(value = "替检人", position = 36)
    private String tjr;

    @ApiModelProperty(value = "是否黑名单：0.否 1.是", position = 37)
    private Integer isHmd;

    @ApiModelProperty(value = "黑名单备注", position = 38)
    private String isHmdb;

    @ApiModelProperty(value = "是否自动已检：0.否 1.是", position = 39)
    private Integer autoExamined;

    @ApiModelProperty(value = "前台须知", position = 40)
    private String qtxz;

    @ApiModelProperty(value = "备注", position = 41)
    private String note;

    @ApiModelProperty(value = "导引单备注", position = 42)
    private String guidancenote;

    @ApiModelProperty(value = "通知方式ID", position = 43)
    private String idInformway;

    @ApiModelProperty(value = "预付方式ID", position = 44)
    private String idPayway;

    @ApiModelProperty(value = "预付方式", position = 45)
    private String payway;

    @ApiModelProperty(value = "预付金额", position = 46)
    private Double prepayment;

    @ApiModelProperty(value = "记账单位", position = 47)
    private String jzdw;

    @ApiModelProperty(value = "记账人", position = 48)
    private String jzdwr;

    @ApiModelProperty(value = "审批人", position = 49)
    private String spr;

    @ApiModelProperty(value = "危害因素", position = 50)
    private String jhys;

    /**
     * @see MedicalType
     */
    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急", position = 51)
    private String medicaltype;

    @ApiModelProperty(value = "工种id", position = 52)
    private String worktypeId;

    @ApiModelProperty(value = "总工龄", position = 53)
    private Integer zgl;

    @ApiModelProperty(value = "接害工龄", position = 54)
    private Integer jhgl;

    @ApiModelProperty(value = "工号", position = 55)
    private String workno;

    @ApiModelProperty(value = "参加工作时间", position = 56)
    private Date workDate;

    @ApiModelProperty(value = "从事该工种时间", position = 57)
    private Date harmDate;

    /**
     * @see CulturalLevel
     */
    @ApiModelProperty(value = "文化水平：0.小学 1.初中 2.技校 3.职高 4.高中 5.中专 6.大专 7.大学 8.研究生以上", position = 58)
    private Integer cultural;

    @ApiModelProperty(value = "邮政编码", position = 59)
    private Integer yzbm;

    @ApiModelProperty(value = "邮寄地址", position = 60)
    private String yjaddress;

    @ApiModelProperty(value = "备选数量", position = 61)
    private Integer bxcount;

    @ApiModelProperty(value = "创建时间", position = 62)
    private Date createdate;

    @ApiModelProperty(value = "修改日期", position = 63)
    private Date modifydate;

    @ApiModelProperty(value = "套餐价格", position = 64)
    private Double tcprice;

    @ApiModelProperty(value = "应付金额", position = 65)
    private Double moneyamount;

    @ApiModelProperty(value = "实付金额", position = 66)
    private Double moneyamountpaid;

    @ApiModelProperty(value = "统收限额", position = 67)
    private Double personpricelimit;

    @ApiModelProperty(value = "登记收费项目列表", position = 68)
    private List<ItemsDto> itemList;

    @ApiModelProperty(value = "是否通知检验科：0.否 1.是", position = 69)
    private Integer noticeJyk;

    @ApiModelProperty(value = "统收费用", position = 70)
    private Double tongFei;

    @ApiModelProperty(value = "现金", position = 71)
    private Double xianjin;

    @ApiModelProperty(value = "版本", position = 72)
    private Long version;

    @ApiModelProperty(value = "复查体检号（第一次的体检号）", position = 73)
    private String inpatientno;

    @ApiModelProperty(value = "补检体检号（上一次体检号）", position = 74)
    private String insuranceno;


    @ApiModelProperty(value = "备单体检号生成人")
    private String guidancenote2;


    @ApiModelProperty(value = "第三方条码  城阳健康证用到了")
    private String thirdCode;

    @ApiModelProperty(value = "SABC等级")
    private String sabc;

}
