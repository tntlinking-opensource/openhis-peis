package com.center.medical.olddata.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检者团体任务(MdPeisorgreservation)表实体类
 *
 * @author ay
 * @since 2023-08-12 11:53:29
 */
@Data
@TableName("md_peisorgreservation")
@ApiModel(value = "MdPeisorgreservation", description = "体检者团体任务实体类")
public class MdPeisorgreservation extends Model<MdPeisorgreservation> implements Serializable {
    private static final long serialVersionUID = 819638187731598034L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

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
    private Integer fRegselectablepaidbyorg;

    @ApiModelProperty(value = "体检者数量")
    private Integer countexaminee;

    @ApiModelProperty(value = "预约体检者数量")
    private Integer countexamineereserved;

    @ApiModelProperty(value = "已登记体检者数量")
    private Integer countexamineeregistered;

    @ApiModelProperty(value = "已全部体检结束体检者数量")
    private Integer countexamineefullfinished;

    @ApiModelProperty(value = "部分完成体检者数量")
    private Integer countexamineepartfinished;

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
    private Integer fFullsettled;

    @ApiModelProperty(value = "F_MoneyPaid")
    private Integer fMoneypaid;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "联系人")
    private String contact;

    @ApiModelProperty(value = "销售员ID")
    private String idSalesperson;

    @ApiModelProperty(value = "团体任务密级")
    private Integer orgresvconfidentiallevel;

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

    @ApiModelProperty(value = "订单ID")
    private String ddh;

    @ApiModelProperty(value = "前台须知")
    private String qtxz;

    @ApiModelProperty(value = "计划结束日期")
    private Date planenddate;

    @ApiModelProperty(value = "联系方式")
    private String phone;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "备单人员ID")
    private String bdid;

    @ApiModelProperty(value = "分中心ID(线下备单)")
    private String fzxid;
}
