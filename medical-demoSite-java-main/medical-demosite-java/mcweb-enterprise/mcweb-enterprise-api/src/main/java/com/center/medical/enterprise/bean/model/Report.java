package com.center.medical.enterprise.bean.model;


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
 * BG报告主表(MdReport)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:17
 */
@Data
@TableName("md_report")
@ApiModel(value = "MdReport", description = "BG报告主表实体类")
public class Report extends Model<Report> implements Serializable {
    private static final long serialVersionUID = 410465237729234062L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "职业/健康")
    private Integer diseaseHealth;

    @ApiModelProperty(value = "是否打印复查通知单")
    private Integer isPrintMessage;

    @ApiModelProperty(value = "报告存放位置（word）")
    private String urlWord;

    @ApiModelProperty(value = "报告存放位置（pdf）")
    private String urlPdf;

    @ApiModelProperty(value = "体检状态，详见：com.world.center.bean.enums.jktjzt")
    private Integer status;

    @ApiModelProperty(value = "是否已总检")
    private Integer isTotal;

    @ApiModelProperty(value = "打印人名称")
    private String printMan;

    @ApiModelProperty(value = "打印人ID")
    private String printId;

    @ApiModelProperty(value = "打印时间")
    private Date printTime;

    @ApiModelProperty(value = "生成次数")
    private Integer createNum;

    @ApiModelProperty(value = "打印次数")
    private Integer printNum;

    @ApiModelProperty(value = "一审人ID")
    private String firstId;

    @ApiModelProperty(value = "一审人名称")
    private String firstMan;

    @ApiModelProperty(value = "一审时间")
    private Date firstTime;

    @ApiModelProperty(value = "一审未通过原因")
    private String firstReason;

    @ApiModelProperty(value = "二审人ID")
    private String secondId;

    @ApiModelProperty(value = "二审人名称")
    private String secondMan;

    @ApiModelProperty(value = "二审时间")
    private Date secondTime;

    @ApiModelProperty(value = "二审未通过原因")
    private String secondReason;

    @ApiModelProperty(value = "终审人ID")
    private String lastId;

    @ApiModelProperty(value = "终审人名称")
    private String lastMan;

    @ApiModelProperty(value = "终审时间")
    private Date lastTime;

    @ApiModelProperty(value = "终审未通过原因")
    private String lastReason;

    @ApiModelProperty(value = "交出人ID")
    private String joinPersonId;

    @ApiModelProperty(value = "交出人名称")
    private String joinPersonMan;

    @ApiModelProperty(value = "交出时间")
    private Date joinTime;

    @ApiModelProperty(value = "接受人ID")
    private String revPersonId;

    @ApiModelProperty(value = "接受人名称")
    private String revPersonMan;

    @ApiModelProperty(value = "接受时间")
    private Date revTime;

    @ApiModelProperty(value = "发放方式ID")
    private String grantId;

    @ApiModelProperty(value = "柜子号")
    private String chestNum;

    @ApiModelProperty(value = "通知结果")
    private String notificationResult;

    @ApiModelProperty(value = "报告人领取ID（内部销售领取用）")
    private String getterId;

    @ApiModelProperty(value = "报告领取人电话")
    private String getterPhone;

    @ApiModelProperty(value = "报告领取人姓名（代领用，内部人领带出来）")
    private String getterName;

    @ApiModelProperty(value = "报告发放人ID")
    private String issueId;

    @ApiModelProperty(value = "反领取ID")
    private String returnId;

    @ApiModelProperty(value = "领取时间")
    private Date getTime;

    @ApiModelProperty(value = "反领取时间")
    private Date returnTime;

    @ApiModelProperty(value = "快递公司ID")
    private String expressId;

    @ApiModelProperty(value = "快递号")
    private String expressNum;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private Integer sex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "开单医生ID")
    private String idOpendoctor;

    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @ApiModelProperty(value = "总检医生ID")
    private String idDoctorfinal;

    @ApiModelProperty(value = "总检医生")
    private String doctorfinalNameR;

    @ApiModelProperty(value = "总检时间")
    private Date datefinalexamed;

    @ApiModelProperty(value = "订单号（个检为：-1）")
    private String numorgresv;

    @ApiModelProperty(value = "同步标志")
    private Integer tbbz;

    @ApiModelProperty(value = "通知时间")
    private Date notifyDate;

    @ApiModelProperty(value = "通知备注")
    private String notifyMemo;

    @ApiModelProperty(value = "报告生成人")
    private String generateName;

    @ApiModelProperty(value = "报告错误提示")
    private String generateHint;

    @ApiModelProperty(value = "报告生成时间")
    private Date generateDate;

    @ApiModelProperty(value = "短号体检号")
    private Integer shortCode;

    @ApiModelProperty(value = "配置文件id")
    private String configId;

    @ApiModelProperty(value = "是否是核酸报告：1是 null不是")
    private Integer isNuclein;

    @ApiModelProperty(value = "通知操作人")
    private String noticer;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
