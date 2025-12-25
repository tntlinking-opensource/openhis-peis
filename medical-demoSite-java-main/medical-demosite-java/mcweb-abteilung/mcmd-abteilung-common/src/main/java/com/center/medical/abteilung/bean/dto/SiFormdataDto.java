package com.center.medical.abteilung.bean.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约、登记、保存右侧列表-保存更新数据
 */
@Data
public class SiFormdataDto implements Serializable {
    private static final long serialVersionUID = 4223380573920629879L;

    @JsonProperty("idTjtc")
    @ApiModelProperty(value = "体检套餐id")
    private String idTjtc;

    @JsonProperty("idOpendoctor")
    @ApiModelProperty(value = "开单医生id")
    private String idOpendoctor;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @JsonProperty("idOrg")
    @ApiModelProperty(value = "团体id")
    private String idOrg;

    @JsonProperty("orgName")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @JsonProperty("idOrgreservationgroup")
    @ApiModelProperty(value = "分组id")
    private String idOrgreservationgroup;

    @JsonProperty("idMarriage")
    @ApiModelProperty(value = "婚姻id")
    private Integer idMarriage;

    @JsonProperty("idNation")
    @ApiModelProperty(value = "民族id")
    private String idNation;

    @ApiModelProperty(value = "档案号")
    private String patientarchiveno;


    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "vip、vvip等等")
    private String memberlevel;

    @JsonProperty("idPatientclass")
    @ApiModelProperty(value = "体检者类型ID")
    private String idPatientclass;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "是否黑名单")
    private Integer ishmd;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "黑名单备注")
    private String hmdbz;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "创建人")
    private String membercreate;

    @JsonProperty("inputCode")
    @ApiParam(hidden = true)
    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @JsonProperty("isOrg")
    @ApiParam(hidden = true)
    @ApiModelProperty(value = "档案是否团检（0：个人 1：团检）")
    private String isOrg;

    @ApiModelProperty(value = "备注")
    private String note;

    @JsonProperty("hasTen")
    @ApiModelProperty(value = "hasTen")
    private String hasTen;

    @ApiModelProperty(value = "团检")
    private String org;

    @JsonProperty("tongFei")
    @ApiModelProperty(value = "tongFei")
    private Double tongFei;

    @ApiModelProperty(value = "备选数量")
    private String bxcount;

    @JsonProperty("tenFei")
    @ApiModelProperty(value = "十周年")
    private String tenFei;

    @JsonProperty("orgDepart")
    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @ApiModelProperty(value = "orgreservationgroupname")
    private String orgreservationgroupname;

    @ApiModelProperty(value = "体检时间")
    private Date medicaldate;

    @ApiModelProperty(value = "预约时间")
    private Date dateguidancereturned;

    @JsonProperty("fIsforreserve")
    @ApiModelProperty(value = "FIsforreserve")
    private String fIsforreserve;

    @JsonProperty("fRegistered")
    @ApiModelProperty(value = "是否已登记：0或null.否 1.是")
    private String fRegistered;

    @ApiModelProperty(value = "金钱总价")
    private String moneyamount;

    @ApiModelProperty(value = "已支付金额")
    private String moneyamountpaid;

    @JsonProperty("tMoney")
    @ApiModelProperty(value = "套餐价格")
    private String tMoney;

    @JsonProperty("moreChangeMon")
    @ApiModelProperty(value = "moreChangeMon")
    private String moreChangeMon;

    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @ApiModelProperty(value = "id")
    private String id;

    @JsonProperty("idPatientarchive")
    @ApiModelProperty(value = "档案ID")
    private String idPatientarchive;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @JsonProperty("idExamtype")
    @ApiModelProperty(value = "体检类型ID，详见：ExamType")
    private String idExamtype;

    @JsonProperty("idSex")
    @ApiModelProperty(value = "性别")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "生日")
    private Date birthdate;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "qtxz")
    private String qtxz;

    @JsonProperty("isHmd")
    @ApiModelProperty(value = "isHmd")
    private String isHmd;

    @JsonProperty("isHmdb")
    @ApiModelProperty(value = "isHmdb")
    private String isHmdb;

    @ApiModelProperty(value = "工号")
    private String workno;

    @ApiModelProperty(value = "trades")
    private String trades;

    @JsonProperty("workDate")
    @ApiModelProperty(value = "workDate")
    private Date workDate;

    @JsonProperty("harmDate")
    @ApiModelProperty(value = "harmDate")
    private Date harmDate;

    @ApiModelProperty(value = "zgl")
    private String zgl;

    @ApiModelProperty(value = "jhgl")
    private String jhgl;

    @ApiModelProperty(value = "jhys")
    private String jhys;

    @ApiModelProperty(value = "prepayment")
    private String prepayment;

    @ApiModelProperty(value = "yzbm")
    private String yzbm;

    @ApiModelProperty(value = "yjaddress")
    private String yjaddress;

    @ApiModelProperty(value = "guidancenote")
    private String guidancenote;

    @ApiModelProperty(value = "jzdw")
    private String jzdw;

    @ApiModelProperty(value = "jzdwr")
    private String jzdwr;

    @ApiModelProperty(value = "spr")
    private String spr;

    @ApiModelProperty(value = "address")
    private String address;

    @ApiModelProperty(value = "tcprice")
    private String tcprice;


    @ApiModelProperty(value = "xianjin")
    private String xianjin;

    @ApiModelProperty(value = "picture")
    private String picture;
}
