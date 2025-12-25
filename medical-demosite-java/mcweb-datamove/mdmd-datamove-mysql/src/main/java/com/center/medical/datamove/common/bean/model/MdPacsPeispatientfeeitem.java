package com.center.medical.datamove.common.bean.model;


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
 * PACS-体检者收费项目表(MdPacsPeispatientfeeitem)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:35
 */
@Data
@TableName("md_pacs_peispatientfeeitem")
@ApiModel(value = "MdPacsPeispatientfeeitem", description = "PACS-体检者收费项目表实体类")
public class MdPacsPeispatientfeeitem extends Model<MdPacsPeispatientfeeitem> implements Serializable {
    private static final long serialVersionUID = -45003113491674159L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "序号")
    private String idPatientfeeitem;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "体检号")
    private String idPatient;

    @ApiModelProperty(value = "基础数据的收费项目ID")
    private String idExamfeeitem;

    @ApiModelProperty(value = "收费项目名称,必填/如果基础里面名称没填，此字段填“未命名”，查id未找到此字段填为“未知项”")
    private String examfeeitemName;

    @ApiModelProperty(value = "原始价格")
    private Double price;

    @ApiModelProperty(value = "优惠价格")
    private Double factprice;

    @ApiModelProperty(value = "结算价格")
    private Double settleprice;

    @ApiModelProperty(value = "新增项目")
    private Integer fAddeditem;

    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;

    @ApiModelProperty(value = "已登记")
    private Integer fRegistered;

    @ApiModelProperty(value = "登记员ID")
    private String idDoctorreg;

    @ApiModelProperty(value = "登记员")
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

    @ApiModelProperty(value = "收费员姓名")
    private String feechargerNameR;

    @ApiModelProperty(value = "收费时间")
    private Date feechargetime;

    @ApiModelProperty(value = "打折者ID")
    private String idFeediscounter;

    @ApiModelProperty(value = "检验: 批次")
    private Integer batchnumber;

    @ApiModelProperty(value = "检验: 管位")
    private Integer tubeposition;

    @ApiModelProperty(value = "检验: 样本号")
    private Integer samplenumber;

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

    @ApiModelProperty(value = "弃检 0 或者null：未弃检 1：弃检")
    private Integer fGiveup;

    @ApiModelProperty(value = "是否检查：0：未检；1：已检；")
    private Integer fExaminated;

    @ApiModelProperty(value = "体检者检查科室")
    private String idPatientexamdepart;

    @ApiModelProperty(value = "分科检查医师ID")
    private String idExamdoctor;

    @ApiModelProperty(value = "分科检查医师")
    private String examdoctorNameR;

    @ApiModelProperty(value = "检查时间")
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

    @ApiModelProperty(value = "迟检  1为迟捡")
    private String fDelayed;

    @ApiModelProperty(value = "dtDelayedtill")
    private Date dtDelayedtill;

    @ApiModelProperty(value = "notewhydelayed")
    private String notewhydelayed;

    @ApiModelProperty(value = "idExamplace")
    private String idExamplace;

    @ApiModelProperty(value = "补检状态 0: 未补检 1：已补检")
    private String fTransferedhl7;

    @ApiModelProperty(value = "qty")
    private Double qty;

    @ApiModelProperty(value = "feeitemdesc")
    private String feeitemdesc;

    @ApiModelProperty(value = "feeitemsummary")
    private String feeitemsummary;

    @ApiModelProperty(value = "idTypist")
    private String idTypist;

    @ApiModelProperty(value = "idExamapprovedby")
    private String idExamapprovedby;

    @ApiModelProperty(value = "examapprovedbyNameR")
    private String examapprovedbyNameR;

    @ApiModelProperty(value = "word报告路径")
    private String samplenumberfromlis;

    @ApiModelProperty(value = "pdf报告路径")
    private String samplemsgfromlis;

    @ApiModelProperty(value = "receiptsheetnofromhis")
    private String receiptsheetnofromhis;

    @ApiModelProperty(value = "feeitemrequestno")
    private String feeitemrequestno;

    @ApiModelProperty(value = "下载标志   0未同步，1已同步,2更新")
    private String samplestatus;

    @ApiModelProperty(value = "backupsingleitemcopiesprinted")
    private String backupsingleitemcopiesprinted;

    @ApiModelProperty(value = "性别")
    private Integer fFeechargedinttrans;

    @ApiModelProperty(value = "giveupnotelet")
    private String giveupnotelet;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "更新时间")
    private Date modifyDate;

    @ApiModelProperty(value = "是否加项")
    private Integer sfjx;

    @ApiModelProperty(value = "加项医师")
    private String jxys;

    @ApiModelProperty(value = "科室ID")
    private String idKs;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "换项（相当于删除本项的标记）退项")
    private Integer changeItem;

    @ApiModelProperty(value = "是否是最小套餐 0：不是 1：是")
    private Integer isMintc;

    @ApiModelProperty(value = "是否备选 0： 不是 1： 是")
    private Integer isbx;

    @ApiModelProperty(value = "备选数量")
    private Integer bxcount;

    @ApiModelProperty(value = "退费价格")
    private Double endtuiprice;

    @ApiModelProperty(value = "实际金额")
    private Double actualprice;

    @ApiModelProperty(value = " 短体检号")
    private Integer shortCode;

    @ApiModelProperty(value = "据检状态  1据检   null未据检")
    private Integer sfjj;
}
