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
 * 体检团体任务表(Peisorgreservation)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:04
 */
@Data
@TableName("PEISORGRESERVATION")
@ApiModel(value = "Peisorgreservation", description = "体检团体任务表实体类")
public class Peisorgreservation extends Model<Peisorgreservation> implements Serializable {
    private static final long serialVersionUID = 483496033683221096L;

    @ApiModelProperty(value = "自增ID")
    private String idOrgreservation;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "团体任务预定时间")
    private Date datereservation;

    @ApiModelProperty(value = "DateAutoClose")
    private Date dateautoclose;

    @ApiModelProperty(value = "最后结帐时间")
    private Date datefinalsettled;

    @ApiModelProperty(value = "是否招工备选")
    private Integer regselectable;

    @ApiModelProperty(value = "F_RegSelectablePaidByOrg")
    private Double fRegselectablepaidbyorg;

    @ApiModelProperty(value = "体检者数量")
    private Double countexaminee;

    @ApiModelProperty(value = "预约体检者数量")
    private Double countexamineereserved;

    @ApiModelProperty(value = "已登记体检者数量")
    private Double countexamineeregistered;

    @ApiModelProperty(value = "已全部体检结束体检者数量")
    private Double countexamineefullfinished;

    @ApiModelProperty(value = "部分完成体检者数量")
    private Double countexamineepartfinished;

    @ApiModelProperty(value = "金额")
    private Double moneyamount;

    @ApiModelProperty(value = "MoneyAmountSettled")
    private Double moneyamountsettled;

    @ApiModelProperty(value = "优惠金额")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "收费员ID")
    private String idFeecharger;

    @ApiModelProperty(value = "优惠人员ID")
    private String idFeediscounter;

    @ApiModelProperty(value = "任务已完成")
    private Integer fFinished;

    @ApiModelProperty(value = "F_FullSettled")
    private Double fFullsettled;

    @ApiModelProperty(value = "F_MoneyPaid")
    private Double fMoneypaid;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "联系人")
    private String contact;

    @ApiModelProperty(value = "销售员ID")
    private String idSalesperson;

    @ApiModelProperty(value = "团体任务密级")
    private Double orgresvconfidentiallevel;

    @ApiModelProperty(value = "团体任务隐藏")
    private Integer fOrgresvhided;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "PatientCardNo")
    private String patientcardno;

    @ApiModelProperty(value = "InstanceTag")
    private String instancetag;

    @ApiModelProperty(value = "OrgTaskHisCode")
    private String orgtaskhiscode;

    @ApiModelProperty(value = "体检团体类型")
    private String idOrgclass;

    @ApiModelProperty(value = "订单id")
    private String ddh;

    @ApiModelProperty(value = "前台须知")
    private String qtxz;

    @ApiModelProperty(value = "计划结束日期")
    private Date planenddate;

    @ApiModelProperty(value = "团体联系方式")
    private String phone;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "备单人员ID")
    private String bdid;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;
}
