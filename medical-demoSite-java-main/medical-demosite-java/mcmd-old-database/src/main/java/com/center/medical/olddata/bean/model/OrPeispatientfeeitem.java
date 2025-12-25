package com.center.medical.olddata.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检者收费项目表(Peispatientfeeitem)表实体类
 *
 * @author ay
 * @since 2023-08-12 11:55:00
 */
@Data
@TableName("PEISPATIENTFEEITEM")
@ApiModel(value = "Peispatientfeeitem", description = "体检者收费项目表实体类")
public class OrPeispatientfeeitem extends Model<OrPeispatientfeeitem> implements Serializable {
    private static final long serialVersionUID = 243162156821117518L;

    @ApiModelProperty(value = "序号")
    private String idPatientfeeitem;

    @ApiModelProperty(value = "体检号")
    private String idPatient;

    @ApiModelProperty(value = "体检者收费项目ID")
    private String idExamfeeitem;

    @ApiModelProperty(value = "收费项目名称,必填/如果基础里面名称没填，此字段填“未命名”，查id未找到此字段填为“未知项”")
    private String examfeeitemName;

    @ApiModelProperty(value = "原始价格")
    private Double price;

    @ApiModelProperty(value = "优惠价格")
    private Double factprice;

    @ApiModelProperty(value = "结算价格(目前仅用于团检)")
    private Double settleprice;

    @ApiModelProperty(value = "新增项目(不再使用)")
    private Integer fAddeditem;

    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;

    @ApiModelProperty(value = "已登记")
    private Integer fRegistered;

    @ApiModelProperty(value = "登记员ID")
    private String idDoctorreg;

    @ApiModelProperty(value = "登记员(冗余)")
    private String doctorregR;

    @ApiModelProperty(value = "登记时间")
    private Date registertime;

    @ApiModelProperty(value = "登记单ID")
    private String idPatientregistersheet;

    @ApiModelProperty(value = "已退登记")
    private Integer fRegreturned;

    @ApiModelProperty(value = "已收费")
    private Integer fFeecharged;

    @ApiModelProperty(value = "收费员ID")
    private String idFeecharger;

    @ApiModelProperty(value = "收费员姓名(冗余)")
    private String feechargerNameR;

    @ApiModelProperty(value = "收费时间")
    private Date feechargetime;

    @ApiModelProperty(value = "打折者ID")
    private String idFeediscounter;

    @ApiModelProperty(value = "检验: 批次")
    private Double batchnumber;

    @ApiModelProperty(value = "检验: 管位")
    private Double tubeposition;

    @ApiModelProperty(value = "检验: 样本号")
    private Double samplenumber;

    @ApiModelProperty(value = "检验: 已采样")
    private Integer fLabsampled;

    @ApiModelProperty(value = "采样者ID")
    private String idLabsampler;

    @ApiModelProperty(value = "检验: 采样者")
    private String labsamplerNameR;

    @ApiModelProperty(value = "检验: 采样时间")
    private Date labsampletime;

    @ApiModelProperty(value = "检验: 已发至LIS")
    private Integer fLabsendtolis;

    @ApiModelProperty(value = "检验: 发至LIS时间")
    private Date labsendtolistime;

    @ApiModelProperty(value = "检验: 已自LIS收到结果")
    private Integer fLabrcvdfromlis;

    @ApiModelProperty(value = "检验: 收到LIS结果时间")
    private Date labrcvdfromlistime;

    @ApiModelProperty(value = "弃检")
    private Integer fGiveup;

    @ApiModelProperty(value = "0：未检；1：已检；")
    private Integer fExaminated;

    @ApiModelProperty(value = "体检者检查科室")
    private String idPatientexamdepart;

    @ApiModelProperty(value = "分科检查医师ID")
    private String idExamdoctor;

    @ApiModelProperty(value = "分科检查医师")
    private String examdoctorNameR;

    @ApiModelProperty(value = "分科检查时间")
    private Date examinatetime;

    @ApiModelProperty(value = "退费拟退标志")
    private Integer fMarkFeereturn;

    @ApiModelProperty(value = "修改标志(内部使用)")
    private Integer fWorkInnerModify;

    @ApiModelProperty(value = "重症级")
    private Integer severedegree;

    @ApiModelProperty(value = "阳性小结")
    private String positivesummary;

    @ApiModelProperty(value = "接口标志")
    private String interfacemarks;

    @ApiModelProperty(value = "结果URL")
    private String urlresult;

    @ApiModelProperty(value = "F_DELAYED")
    private Integer fDelayed;

    @ApiModelProperty(value = "DT_DELAYEDTILL")
    private Date dtDelayedtill;

    @ApiModelProperty(value = "NOTEWHYDELAYED")
    private String notewhydelayed;

    @ApiModelProperty(value = "ID_EXAMPLACE")
    private String idExamplace;

    @ApiModelProperty(value = "补检状态 0: 未补检 1：已补检")
    private Integer fTransferedhl7;

    @ApiModelProperty(value = "QTY")
    private String qty;

    @ApiModelProperty(value = "FEEITEMDESC")
    private String feeitemdesc;

    @ApiModelProperty(value = "FEEITEMSUMMARY")
    private String feeitemsummary;

    @ApiModelProperty(value = "ID_TYPIST")
    private String idTypist;

    @ApiModelProperty(value = "ID_EXAMAPPROVEDBY")
    private String idExamapprovedby;

    @ApiModelProperty(value = "EXAMAPPROVEDBY_NAME_R")
    private String examapprovedbyNameR;

    @ApiModelProperty(value = "SAMPLENUMBERFROMLIS")
    private String samplenumberfromlis;

    @ApiModelProperty(value = "SAMPLEMSGFROMLIS")
    private String samplemsgfromlis;

    @ApiModelProperty(value = "RECEIPTSHEETNOFROMHIS")
    private String receiptsheetnofromhis;

    @ApiModelProperty(value = "FEEITEMREQUESTNO")
    private String feeitemrequestno;

    @ApiModelProperty(value = "SAMPLESTATUS")
    private String samplestatus;

    @ApiModelProperty(value = "BACKUPSINGLEITEMCOPIESPRINTED")
    private String backupsingleitemcopiesprinted;

    @ApiModelProperty(value = "F_FEECHARGEDINTTRANS")
    private Integer fFeechargedinttrans;

    @ApiModelProperty(value = "GIVEUPNOTELET")
    private String giveupnotelet;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "更新时间")
    private Date modifyDate;

    @ApiModelProperty(value = "0:未加项 1：加项")
    private Integer sfjx;

    @ApiModelProperty(value = "加项医师")
    private String jxys;

    @ApiModelProperty(value = "科室ID")
    private String idKs;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "换项")
    private Integer changeItem;

    @ApiModelProperty(value = "0： 不是 1： 是")
    private Integer isMintc;

    @ApiModelProperty(value = "0： 不是 1： 是")
    private Integer isbx;

    @ApiModelProperty(value = "备选数量")
    private Integer bxcount;

    @ApiModelProperty(value = "退费价格")
    private Double endtuiprice;

    @ApiModelProperty(value = "实际金额")
    private Double actualprice;

    @ApiModelProperty(value = "短号体检号")
    private String shortCode;

    @ApiModelProperty(value = "${column.comment}")
    private String qjr;

    @ApiModelProperty(value = "${column.comment}")
    private String bjr;

    @ApiModelProperty(value = "${column.comment}")
    private String cjr;

    @ApiModelProperty(value = "${column.comment}")
    private Date qjsj;

    @ApiModelProperty(value = "${column.comment}")
    private Date bjsj;

    @ApiModelProperty(value = "${column.comment}")
    private Date cjsj;

    @ApiModelProperty(value = "${column.comment}")
    private Integer sfjj;

    @ApiModelProperty(value = "${column.comment}")
    private String jjr;

    @ApiModelProperty(value = "${column.comment}")
    private Date jjsj;

    @ApiModelProperty(value = "${column.comment}")
    private String jjrqm;

    @ApiModelProperty(value = "${column.comment}")
    private Integer itemGroup;

    @ApiModelProperty(value = "${column.comment}")
    private String adiconCode;

    @ApiModelProperty(value = "收费表ID")
    private String chargeId;

    @ApiModelProperty(value = "订单号")
    private String tradeNo;

//    @ApiModelProperty(value = "${column.comment}")
//    private Integer isconfirm;
}
